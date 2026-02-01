package com.talkfrly.multiplatform.ui.screens.createpublication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.publication.CreatePublicationRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePublicationViewModel(
    private val publicationRepository: PublicationRepository,
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
            is CreatePublicationIntent.SetTagInput -> setTagInput(intent.input)
            is CreatePublicationIntent.AddTag -> addTag(intent.tag)
            is CreatePublicationIntent.RemoveTag -> removeTag(intent.tag)
            is CreatePublicationIntent.Submit -> submit()
            is CreatePublicationIntent.NavigateBack -> {} // Handled in UI
        }
    }

    private fun addImages(uris: List<String>) {
        if (uris.isEmpty()) return
        _state.update { it.copy(imageUris = it.imageUris + uris) }
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
        _state.update { it.copy(imageUris = it.imageUris.filterNot { value -> value == uri }) }
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

        val request = CreatePublicationRequest(
            title = currentState.title.ifBlank { content.take(50) },
            content = content,
            publicationType = currentState.selectedType,
            threadId = currentState.threadId,
            isAnonymous = currentState.isAnonymous,
        )

        publicationRepository.createPublication(request)
            .onSuccess {
                // Success handled in UI navigation
                _state.update { it.copy(isSubmitting = false) }
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
