package com.talkfrly.multiplatform.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
import com.talkfrly.multiplatform.data.stream.repository.StreamRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val feedRepository: FeedRepository,
    private val streamRepository: StreamRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Logout -> { }
            is HomeIntent.GetFeed -> getFeed()
            is HomeIntent.GetStreams -> getStreams()
            is HomeIntent.SetSelectedTab -> setSelectedTab(intent.index)
        }
    }

    private fun setSelectedTab(selectedTab: Int) = viewModelScope.launch {
        _state.update { it.copy(selectedTabIndex = selectedTab) }
    }

    private fun getFeed() = viewModelScope.launch {
        startLoading()
        feedRepository.getFeed(
            page = 1,
            limit = 10,
//            filter = PublicationFilter()
        ).onSuccess { feedList ->
            _state.update {
                it.copy(
                    feeds = feedList
                )
            }
        }.onError {
            println("Cannot fetch feeds")
        }.onFinally {
            stopLoading()
        }
    }

    private fun getStreams() = viewModelScope.launch {
        _state.update { it.copy(isLoadingStreams = true) }
        streamRepository.streamList(page = 1, limit = 20)
            .onSuccess { streamList ->
                _state.update {
                    it.copy(
                        streams = streamList.items,
                        isLoadingStreams = false,
                    )
                }
            }.onError {
                _state.update {
                    it.copy(
                        isLoadingStreams = false,
                    )
                }
            }
    }
}
