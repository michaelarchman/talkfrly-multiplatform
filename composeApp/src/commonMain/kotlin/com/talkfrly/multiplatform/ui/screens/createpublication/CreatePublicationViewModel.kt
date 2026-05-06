package com.talkfrly.multiplatform.ui.screens.createpublication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.uploads.ImageUploadStatus
import com.talkfrly.multiplatform.data.uploads.repository.UploadRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.publication.CreatePublicationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePublicationViewModel(
    private val publicationRepository: PublicationRepository,
    private val uploadRepository: UploadRepository,
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreatePublicationState())
    val state: StateFlow<CreatePublicationState> = _state

    fun initialize(threadId: String?, threadName: String?) {
        _state.update {
            it.copy(
                inThreadContext = threadId != null,
                threadId = threadId,
                threadName = threadName
            )
        }
    }

    fun onIntent(intent: CreatePublicationIntent) {
        when (intent) {
            is CreatePublicationIntent.SetType -> setType(intent.type)
            is CreatePublicationIntent.SetTitle -> setTitle(intent.title)
            is CreatePublicationIntent.SetContent -> setContent(intent.content)
            is CreatePublicationIntent.SetAnonymous -> setAnonymous(intent.isAnonymous)
            is CreatePublicationIntent.SetVisibility -> setVisibility(intent.visibility)
            is CreatePublicationIntent.OpenCamera -> {}
            is CreatePublicationIntent.OpenGallery -> {}
            is CreatePublicationIntent.AddImages -> addImages(intent.uris)
            is CreatePublicationIntent.RemoveImage -> removeImage(intent.uri)
            is CreatePublicationIntent.RetryImage -> retryImage(intent.uri)
            is CreatePublicationIntent.SetTagInput -> setTagInput(intent.input)
            is CreatePublicationIntent.AddTag -> addTag(intent.tag)
            is CreatePublicationIntent.RemoveTag -> removeTag(intent.tag)
            is CreatePublicationIntent.Submit -> submit()
            is CreatePublicationIntent.NavigateBack -> {} // Handled in UI
        }
    }

    private fun addImages(uris: List<String>) {
        if (uris.isEmpty()) return
        _state.update { state ->
            val remainingSlots = 4 - state.imageUris.size
            if (remainingSlots <= 0) {
                return@update state.copy(error = "You can upload up to 4 photos")
            }
            val newUris = uris.filterNot { state.imageUris.contains(it) }
                .take(remainingSlots)
            val statusUpdates = newUris.associateWith { ImageUploadStatus.PENDING }
            state.copy(
                imageUris = state.imageUris + newUris,
                imageUploadStatus = state.imageUploadStatus + statusUpdates,
                imageUploadErrors = state.imageUploadErrors - newUris.toSet(),
                uploadedImageUrls = state.uploadedImageUrls - newUris.toSet(),
                error = null,
            )
        }
        val urisToUpload = _state.value.imageUploadStatus
            .filter { it.value == ImageUploadStatus.PENDING }
            .keys
        urisToUpload.forEach { uri ->
            uploadImage(uri)
        }
    }

    private fun setType(type: com.talkfrly.multiplatform.domain.publication.PublicationType) {
        _state.update { it.copy(selectedType = type) }
    }

    private fun setTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    private fun setContent(content: String) {
        _state.update { it.copy(content = content, error = null) }
    }

    private fun setAnonymous(isAnonymous: Boolean) {
        _state.update { it.copy(isAnonymous = isAnonymous) }
    }

    private fun setVisibility(visibility: VisibilityOption) {
        _state.update { it.copy(visibility = visibility) }
    }

    private fun setTagInput(input: String) {
        _state.update { it.copy(tagInput = input) }
    }

    private fun addTag(tag: String) {
        val trimmed = tag.trim()
        if (trimmed.isEmpty()) return

        val currentTags = _state.value.tags
        if (currentTags.size >= 6) return
        if (currentTags.any { it.equals(trimmed, ignoreCase = true) }) return

        _state.update {
            it.copy(
                tags = it.tags + trimmed,
                tagInput = ""
            )
        }
    }

    private fun removeTag(tag: String) {
        _state.update {
            it.copy(tags = it.tags.filter { t -> !t.equals(tag, ignoreCase = true) })
        }
    }

    private fun removeImage(uri: String) {
        _state.update {
            it.copy(
                imageUris = it.imageUris.filterNot { value -> value == uri },
                imageUploadStatus = it.imageUploadStatus - uri,
                imageUploadErrors = it.imageUploadErrors - uri,
                uploadedImageUrls = it.uploadedImageUrls - uri,
            )
        }
    }

    private fun retryImage(uri: String) {
        if (_state.value.imageUris.contains(uri)) {
            uploadImage(uri)
        }
    }

    private fun uploadImage(uri: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                imageUploadStatus = it.imageUploadStatus + (uri to ImageUploadStatus.UPLOADING),
                imageUploadErrors = it.imageUploadErrors - uri,
            )
        }
        uploadRepository.uploadImage(uri)
            .onSuccess { uploadedUrl ->
                _state.update {
                    it.copy(
                        imageUploadStatus = it.imageUploadStatus + (uri to ImageUploadStatus.SUCCESS),
                        uploadedImageUrls = it.uploadedImageUrls + (uri to uploadedUrl),
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        imageUploadStatus = it.imageUploadStatus + (uri to ImageUploadStatus.ERROR),
                        imageUploadErrors = it.imageUploadErrors + (uri to (error.message ?: "Upload failed")),
                    )
                }
            }
    }

    private fun submit() = viewModelScope.launch {
        val currentState = _state.value
        val content = currentState.content.trim()

        if (content.isBlank()) {
            _state.update { it.copy(error = "Content cannot be empty") }
            return@launch
        }

        if (content.length > 5000) {
            _state.update { it.copy(error = "Content exceeds 5000 characters") }
            return@launch
        }

        _state.update { it.copy(isSubmitting = true, error = null) }

        val imageUrls = currentState.imageUris.mapNotNull { currentState.uploadedImageUrls[it] }

        val request = CreatePublicationRequest(
            content = content,
            type = currentState.selectedType.apiValue,
            threadId = currentState.threadId,
            isAnonymous = currentState.isAnonymous,
            imageUrls = imageUrls,
        )

        publicationRepository.createPublication(request)
            .onSuccess {
                // Success handled in UI navigation
                _state.update { it.copy(isSubmitting = false, isSubmitted = true) }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isSubmitting = false,
                        error = error.message
                    )
                }
            }
    }
}
