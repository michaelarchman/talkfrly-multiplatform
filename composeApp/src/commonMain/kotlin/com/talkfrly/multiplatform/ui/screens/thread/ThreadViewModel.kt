package com.talkfrly.multiplatform.ui.screens.thread

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.threads.repository.ThreadRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.thread.ThreadListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreadViewModel(
    private val threadRepository: ThreadRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ThreadState())
    val state: StateFlow<ThreadState> get() = _state

    fun onIntent(intent: ThreadIntent) {
        when (intent) {
            is ThreadIntent.GetThreads -> getThreads()
            is ThreadIntent.GetJoinedThreads -> getJoinedThreads()
            is ThreadIntent.GetOwnedThreads -> getOwnedThreads()
            is ThreadIntent.JoinThread -> joinThread(intent.id)
            is ThreadIntent.LeaveThread -> leaveThread(intent.id)

        }
    }

    private fun getThreads() = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(1, 10, ""))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        allThreads = response.threads,
                        totalCount = response.totalCount,
                        page = response.page,
                        limit = response.limit,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        allThreads = emptyList(),
                        errorMessage = error.message ?: error.error ?: "Failed to load threads",
                    )
                }
            }
            .onFinally {
                _state.update {
                    it.copy(isLoading = false)
                }

                stopLoading()
            }
    }

    private fun joinThread(id: String) = viewModelScope.launch {
        startLoading()
        threadRepository.joinThread(id)
            .onSuccess {
                getThreads()
                getJoinedThreads()
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to join thread",
                    )
                }
            }
            .onFinally { stopLoading() }
    }

    private fun leaveThread(id: String) = viewModelScope.launch {
        startLoading()
        threadRepository.leaveThread(id)
            .onSuccess {
                getThreads()
                getJoinedThreads()
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to leave thread",
                    )
                }
            }
            .onFinally { stopLoading() }
    }

    private fun getJoinedThreads() = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(1, 10, "member"))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        joinedThreads = response.threads,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to load joined threads",
                    )
                }
            }
            .onFinally {
                _state.update {
                    it.copy(isLoading = false)
                }

                stopLoading()
            }
    }

    private fun getOwnedThreads() = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(1, 10, "owner"))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        ownedThreads = response.threads,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to load owned threads",
                    )
                }
            }
            .onFinally {
                _state.update {
                    it.copy(isLoading = false)
                }

                stopLoading()
            }
    }
}