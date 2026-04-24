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
) : BaseViewModel() {
    private val _state = MutableStateFlow(PublicationScreenState())
    val state: StateFlow<PublicationScreenState> get() = _state

    fun onIntent(intent: PublicationScreenIntent) {
        when (intent) {
            is PublicationScreenIntent.GetPublications -> getPublicationDetails(intent.publicationId)
            is PublicationScreenIntent.GetComments -> getComments(intent.publicationId)
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
}
