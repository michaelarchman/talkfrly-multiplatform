package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedTabViewModel(
    private val feedRepository: FeedRepository,
    private val publicationRepository: PublicationRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(FeedTabState())
    val state: StateFlow<FeedTabState> get() = _state

    fun onIntent(intent: FeedTabIntent) {
        when (intent) {
            is FeedTabIntent.GetFeed -> fetchFeed(intent.page, intent.limit)
            is FeedTabIntent.LikePublication -> likePublication(intent.publicationId)
            is FeedTabIntent.UnlikePublication -> unlikePublication(intent.publicationId)
        }
    }

    private fun likePublication(publicationId: String) = viewModelScope.launch {
        _state.update { current ->
            current.copy(
                visiblePublications = current.visiblePublications.map { item ->
                    if (item.id == publicationId) item.copy(likedByUser = true, likeCount = item.likeCount + 1)
                    else item
                }
            )
        }
        publicationRepository.likePublicationById(publicationId)
            .onError {
                _state.update { current ->
                    current.copy(
                        visiblePublications = current.visiblePublications.map { item ->
                            if (item.id == publicationId) item.copy(likedByUser = false, likeCount = item.likeCount - 1)
                            else item
                        }
                    )
                }
            }
    }

    private fun unlikePublication(publicationId: String) = viewModelScope.launch {
        _state.update { current ->
            current.copy(
                visiblePublications = current.visiblePublications.map { item ->
                    if (item.id == publicationId) item.copy(likedByUser = false, likeCount = item.likeCount - 1)
                    else item
                }
            )
        }
        publicationRepository.unlikePublicationById(publicationId)
            .onError {
                _state.update { current ->
                    current.copy(
                        visiblePublications = current.visiblePublications.map { item ->
                            if (item.id == publicationId) item.copy(likedByUser = true, likeCount = item.likeCount + 1)
                            else item
                        }
                    )
                }
            }
    }

    private fun fetchFeed(page: Int, limit: Int) = viewModelScope.launch {
        startLoading()
        feedRepository.getFeed(page = page, limit = limit)
            .onSuccess { newFeed ->
                _state.update { current ->
                    val combined = (current.visiblePublications + newFeed.publications)
                        .distinctBy { it.id }
                    current.copy(
                        feed = newFeed,
                        visiblePublications = combined,
                        hasNextPage = newFeed.publications.size >= newFeed.limit
                    )
                }
            }.onError { error ->
                println("Error during Feed Fetch, $error")
            }.onFinally {
                stopLoading()
            }
    }
}