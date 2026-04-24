package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedTabViewModel(
    private val feedRepository: FeedRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(FeedTabState())
    val state: StateFlow<FeedTabState> get() = _state

    fun onIntent(intent: FeedTabIntent) {
        when (intent) {
            is FeedTabIntent.GetFeed -> fetchFeed(intent.page, intent.limit)
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