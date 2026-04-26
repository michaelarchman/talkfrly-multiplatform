package com.talkfrly.multiplatform.ui.screens.publication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.comments.repository.CommentRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.user.repository.UserRepository
import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicationScreenViewModel(
    private val publicationRepository: PublicationRepository,
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository,
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
        startLoading()
        commentRepository.getComments(publicationId)
            .onSuccess { commentList ->
                _state.update { it.copy(comments = commentList.comments) }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun setNewCommentContent(newCommentContent: String) {
        _state.update { it.copy(newCommentContent = newCommentContent) }
    }

    private fun postComment(createCommentRequest: CreateCommentRequest) = viewModelScope.launch {
        startLoading()
        commentRepository.createComment(createCommentRequest)
            .onSuccess { comment ->
                _state.update { it.copy(comments = it.comments.orEmpty() + comment) }
            }
            .onError { error ->
                println(error)
            }
            .onFinally {
                stopLoading()
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
}
