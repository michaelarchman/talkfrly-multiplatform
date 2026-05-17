package com.talkfrly.multiplatform.ui.screens.publication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.comments.repository.CommentRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.ranking.repository.RankingRepository
import com.talkfrly.multiplatform.data.uploads.ImageUploadStatus
import com.talkfrly.multiplatform.data.uploads.repository.UploadRepository
import com.talkfrly.multiplatform.data.user.repository.UserRepository
import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.ranking.RankedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicationScreenViewModel(
    private val publicationRepository: PublicationRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
    private val rankingRepository: RankingRepository,
    private val uploadRepository: UploadRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(PublicationScreenState())
    val state: StateFlow<PublicationScreenState> get() = _state

    fun onIntent(intent: PublicationScreenIntent) {
        when (intent) {
            is PublicationScreenIntent.GetCurrentUser -> fetchCurrentUser()
            is PublicationScreenIntent.GetPublications -> getPublicationDetails(intent.publicationId)
            is PublicationScreenIntent.GetComments -> getComments(intent.publicationId)
            is PublicationScreenIntent.SetNewCommentContent -> setNewCommentContent(intent.content)
            is PublicationScreenIntent.PostComment -> postComment(intent.createCommentRequest)
            is PublicationScreenIntent.LikePublication -> likePublication(intent.publicationId)
            is PublicationScreenIntent.UnlikePublication -> unlikePublication(intent.publicationId)
            is PublicationScreenIntent.VoteRankingItem -> voteRankingItem(intent.publicationId, intent.itemId, intent.value)
            is PublicationScreenIntent.AddImage -> addImage(intent.uri)
            is PublicationScreenIntent.RemoveImage -> removeImage()
            is PublicationScreenIntent.RetryImage -> retryImage(intent.uri)
            is PublicationScreenIntent.UpdateComment -> updateComment(intent.content, intent.commentId)
            is PublicationScreenIntent.DeleteComment -> deleteComment(intent.commentId, intent.publicationId)
            is PublicationScreenIntent.DeletePublication -> deletePublication(intent.id)
        }
    }

    private fun fetchCurrentUser() = viewModelScope.launch {
        startLoading()
        userRepository.getCurrentUser()
            .onSuccess { result ->
                _state.update {
                    it.copy(currentUser = result)
                }
            }.onError {
                println("Failed to fetchCurrentUser()")
            }.onFinally {
                stopLoading()
            }
    }

    private fun getPublicationDetails(publicationId: String) = viewModelScope.launch {
        startLoading()
        publicationRepository.getPublicationById(publicationId)
            .onSuccess { result ->
                _state.update { it.copy(publication = result) }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun getComments(publicationId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoadingComments = true) }
        commentRepository.getComments(publicationId)
            .onSuccess { commentList ->
                _state.update { it.copy(comments = commentList.comments) }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                _state.update { it.copy(isLoadingComments = false) }
            }
    }

    private fun setNewCommentContent(newCommentContent: String) {
        _state.update { it.copy(newCommentContent = newCommentContent) }
    }

    private fun postComment(createCommentRequest: CreateCommentRequest) = viewModelScope.launch {
        _state.update { it.copy(isPostingComment = true) }
        commentRepository.createComment(createCommentRequest)
            .onSuccess {
                _state.update {
                    it.copy(
                        newCommentContent = "",
                        imageUri = null,
                        imageUploadStatus = null,
                        imageUploadError = null,
                        uploadedImageUrl = null,
                    )
                }
                getComments(createCommentRequest.publicationId)
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                _state.update { it.copy(isPostingComment = false) }
            }
    }

    private fun updateComment(updateCommentRequest: CreateCommentRequest, commentId: String) = viewModelScope.launch {
        _state.update { it.copy(isPostingComment = true) }
        commentRepository.updateComment(commentId, updateCommentRequest)
            .onSuccess {
                _state.update {
                    it.copy(
                        newCommentContent = "",
                        imageUri = null,
                        imageUploadStatus = null,
                        imageUploadError = null,
                        uploadedImageUrl = null,
                    )
                }
                getComments(updateCommentRequest.publicationId)
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                _state.update { it.copy(isPostingComment = false) }
            }
    }

    private fun deleteComment(commentId: String, publicationId: String) = viewModelScope.launch {
        _state.update { it.copy(isPostingComment = true) }
        commentRepository.deleteComment(commentId)
            .onSuccess {
                getComments(publicationId)
            }.onError { error ->
                println(error)
            }.onFinally {
                _state.update { it.copy(isPostingComment = false) }
            }
    }

    private fun likePublication(publicationId: String) = viewModelScope.launch {
        startLoading()
        publicationRepository.likePublicationById(publicationId)
            .onSuccess {
                _state.update {
                    it.copy(
                        publication = it.publication?.copy(
                            likedByUser = true,
                            likeCount = it.publication.likeCount + 1
                        )
                    )
                }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun unlikePublication(publicationId: String) = viewModelScope.launch {
        startLoading()
        publicationRepository.unlikePublicationById(publicationId)
            .onSuccess {
                _state.update {
                    it.copy(
                        publication = it.publication?.copy(
                            likedByUser = false,
                            likeCount = it.publication.likeCount - 1
                        )
                    )
                }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun voteRankingItem(
        publicationId: String,
        itemId: String,
        value: Int,
    ) = viewModelScope.launch {
        val previousPublication = state.value.publication ?: return@launch

        _state.update { current ->
            current.copy(publication = current.publication?.withUpdatedRankingVote(itemId, value))
        }

        val result = if (value == 0) {
            rankingRepository.removeVote(publicationId, itemId)
        } else {
            rankingRepository.vote(publicationId, itemId, value)
        }

        result.onError {
            _state.update { current ->
                current.copy(publication = previousPublication)
            }
        }
    }

    private fun addImage(uri: String) = viewModelScope.launch {
        startLoading()
        _state.update {
            it.copy(
                imageUri = uri,
                imageUploadStatus = ImageUploadStatus.UPLOADING,
                imageUploadError = null,
                uploadedImageUrl = null,
            )
        }
        uploadRepository.uploadImage(uri)
            .onSuccess { uploadedUrl ->
                _state.update {
                    it.copy(
                        imageUploadStatus = ImageUploadStatus.SUCCESS,
                        uploadedImageUrl = uploadedUrl
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        imageUploadStatus = ImageUploadStatus.ERROR,
                        imageUploadError = error.message ?: "Upload failed"
                    )
                }
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun retryImage(uri: String) = viewModelScope.launch {
        if (_state.value.imageUri == uri) {
            addImage(uri)
        }
    }

    private fun removeImage() = viewModelScope.launch {
        _state.update {
            it.copy(
                imageUri = null,
                imageUploadStatus = null,
                imageUploadError = null,
                uploadedImageUrl = null
            )
        }
    }

    private fun deletePublication(id: String) = viewModelScope.launch {
        if (_state.value.isDeletingPublication) return@launch

        _state.update {
            it.copy(
                isDeletingPublication = true,
                deletePublicationError = null,
            )
        }
        publicationRepository.deletePublication(id)
            .onSuccess {
                _state.update { it.copy(isPublicationDeleted = true) }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        deletePublicationError = error.message ?: "Failed to delete publication",
                    )
                }
            }
            .onFinally {
                _state.update {
                    it.copy(isDeletingPublication = false)
                }
            }
    }
}

private fun Publication.withUpdatedRankingVote(
    itemId: String,
    nextVote: Int,
): Publication {
    val currentRanking = ranking ?: return this
    val updatedItems = currentRanking.items.map { rankedItem ->
        if (rankedItem.id != itemId) return@map rankedItem
        rankedItem.withUpdatedVote(nextVote, currentRanking.noNegativeScores)
    }

    return copy(ranking = currentRanking.copy(items = updatedItems))
}

private fun RankedItem.withUpdatedVote(
    nextVote: Int,
    noNegativeScores: Boolean,
): RankedItem {
    val previousVote = userVote ?: 0
    val nextUserVote = nextVote.takeIf { it != 0 }
    val scoreDelta = nextVote - previousVote
    val updatedScore = if (noNegativeScores) {
        (score + scoreDelta).coerceAtLeast(0)
    } else {
        score + scoreDelta
    }

    return copy(
        score = updatedScore,
        userVote = nextUserVote,
    )
}
