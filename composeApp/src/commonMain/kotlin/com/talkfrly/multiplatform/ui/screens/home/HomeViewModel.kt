package com.talkfrly.multiplatform.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.stream.repository.StreamRepository
import com.talkfrly.multiplatform.data.user.repository.UserRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val userRepository: UserRepository,
    private val streamRepository: StreamRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.GetCurrentUser -> fetchCurrentUser()
            is HomeIntent.GetStreams -> getStreams()
            is HomeIntent.SetSelectedTab -> setSelectedTab(intent.index)
        }
    }

    private fun setSelectedTab(selectedTab: Int) = viewModelScope.launch {
        _state.update { it.copy(selectedTabIndex = selectedTab) }
    }

    private fun fetchCurrentUser() = viewModelScope.launch {
        startLoading()
        userRepository.getCurrentUser()
            .onSuccess { result ->
                _state.update { it.copy(currentUser = result) }
            }.onError {

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
