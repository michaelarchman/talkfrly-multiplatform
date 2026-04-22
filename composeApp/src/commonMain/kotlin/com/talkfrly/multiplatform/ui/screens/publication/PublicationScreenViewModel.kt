package com.talkfrly.multiplatform.ui.screens.publication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.comments.repository.CommentRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepository
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
    private val threadRepository: ThreadRepository,
    private val userRepository: UserRepository, // UserRepository2
) : BaseViewModel() {
    private val _state = MutableStateFlow(PublicationScreenState())
    val state: StateFlow<PublicationScreenState> get() = _state

    private var publicationId: String = ""

    fun initialize(publicationId: String) {
        this.publicationId = publicationId
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        userRepository.getCurrentUser()
            .onSuccess { user ->
                _state.update { it.copy(
                    currentUserId = user.id,
                    isAdmin = user.isAdmin
                ) }
            }
            .onError { error ->
                println("Failed to get current user: $error")
            }
    }

    fun onIntent(intent: PublicationScreenIntent) {
        when (intent) {
            is PublicationScreenIntent.GetPublicationScreen -> getPublicationDetails()
            is PublicationScreenIntent.NavigateBack -> { }
            is PublicationScreenIntent.GetComments -> getComments()
            is PublicationScreenIntent.UpdateCommentFormContent ->
                _state.update { it.copy(commentFormContent = intent.content) }
            is PublicationScreenIntent.UpdateCommentFormIsAnonymous ->
                _state.update { it.copy(commentFormIsAnonymous = intent.isAnonymous) }
            is PublicationScreenIntent.SubmitComment -> submitComment()
            is PublicationScreenIntent.StartReply ->
                _state.update { it.copy(replyingTo = intent.comment) }
            is PublicationScreenIntent.CancelReply ->
                _state.update { it.copy(
                    replyingTo = null,
                    replyFormContent = "",
                    replyFormIsAnonymous = false,
                ) }
            is PublicationScreenIntent.UpdateReplyFormContent ->
                _state.update { it.copy(replyFormContent = intent.content) }
            is PublicationScreenIntent.UpdateReplyFormIsAnonymous ->
                _state.update { it.copy(replyFormIsAnonymous = intent.isAnonymous) }
            is PublicationScreenIntent.SubmitReply -> submitReply()
            is PublicationScreenIntent.JoinThread -> joinThread()
            is PublicationScreenIntent.ToggleMenu ->
                _state.update { it.copy(isMenuExpanded = !it.isMenuExpanded) }
            is PublicationScreenIntent.EditPost -> editPost()
            is PublicationScreenIntent.DeletePost -> deletePost()
            is PublicationScreenIntent.ReportPost -> reportPost()
        }
    }

    private fun getPublicationDetails() = viewModelScope.launch {
        startLoading()
        publicationRepository.getPublicationById(publicationId)
            .onSuccess { publication ->
                _state.update { it.copy(publication = publication, errorMessage = null) }
                getComments()
            }
            .onError { error ->
                _state.update { it.copy(errorMessage = "Failed to load publication") }
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun getComments() = viewModelScope.launch {
        _state.update { it.copy(isLoadingComments = true) }
        commentRepository.getComments(publicationId)
            .onSuccess { commentList ->
                _state.update { it.copy(
                    comments = commentList.comments,
                    isLoadingComments = false,
                    commentsError = null,
                ) }
            }
            .onError { error ->
                _state.update { it.copy(
                    isLoadingComments = false,
                    commentsError = "Failed to load comments",
                ) }
                println(error)
            }
    }

    private fun submitComment() = viewModelScope.launch {
        val content = _state.value.commentFormContent.trim()
        if (content.isEmpty()) return@launch

        _state.update { it.copy(isSubmittingComment = true) }

        val request = CreateCommentRequest(
            publicationId = publicationId,
            content = content,
            isAnonymous = _state.value.commentFormIsAnonymous,
        )

        commentRepository.createComment(request)
            .onSuccess { newComment ->
                _state.update { it.copy(
                    comments = listOf(newComment) + it.comments,
                    commentFormContent = "",
                    commentFormIsAnonymous = false,
                    isSubmittingComment = false,
                ) }
            }
            .onError { error ->
                _state.update { it.copy(
                    isSubmittingComment = false,
                    commentsError = "Failed to post comment",
                ) }
                println(error)
            }
    }

    private fun submitReply() = viewModelScope.launch {
        val content = _state.value.replyFormContent.trim()
        val parentComment = _state.value.replyingTo
        if (content.isEmpty() || parentComment == null) return@launch

        _state.update { it.copy(isSubmittingReply = true) }

        val request = CreateCommentRequest(
            publicationId = publicationId,
            content = content,
            isAnonymous = _state.value.replyFormIsAnonymous,
            parentCommentId = parentComment.id,
        )

        commentRepository.createComment(request)
            .onSuccess { newReply ->
                _state.update { state ->
                    val updatedComments = state.comments.map { comment ->
                        if (comment.id == parentComment.id) {
                            comment.copy(replies = comment.replies + newReply)
                        } else comment
                    }
                    state.copy(
                        comments = updatedComments,
                        replyingTo = null,
                        replyFormContent = "",
                        replyFormIsAnonymous = false,
                        isSubmittingReply = false,
                    )
                }
            }
            .onError { error ->
                _state.update { it.copy(
                    isSubmittingReply = false,
                    commentsError = "Failed to post reply",
                ) }
                println(error)
            }
    }

    private fun joinThread() = viewModelScope.launch {
        val publication = _state.value.publication ?: return@launch
        val threadId = publication.threadId ?: return@launch

        _state.update { it.copy(isJoiningThread = true) }

        threadRepository.joinThread(threadId)
            .onSuccess {
                publicationRepository.getPublicationById(publicationId)
                    .onSuccess { updatedPublication ->
                        _state.update { it.copy(
                            publication = updatedPublication,
                            isJoiningThread = false,
                        ) }
                        getComments()
                    }
            }
            .onError { error ->
                _state.update { it.copy(
                    isJoiningThread = false,
                    commentsError = "Failed to join thread",
                ) }
                println(error)
            }
    }

    private fun editPost() {
        _state.update { it.copy(isMenuExpanded = false) }
        // TODO: Navigate to edit screen
        println("Edit post: $publicationId")
    }

    private fun deletePost() = viewModelScope.launch {
        _state.update { it.copy(isMenuExpanded = false) }
        startLoading()
        publicationRepository.deletePublication(publicationId)
            .onSuccess {
                _state.update { it.copy(errorMessage = "Publication deleted") }
            }
            .onError { error ->
                _state.update { it.copy(errorMessage = "Failed to delete publication") }
                println(error)
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun reportPost() {
        _state.update { it.copy(isMenuExpanded = false) }
        // TODO: Implement report functionality
        println("Report post: $publicationId")
    }
}