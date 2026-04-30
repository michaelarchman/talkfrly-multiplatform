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
            ThreadIntent.GetThreads -> getThreads()
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
                        threads = response.threads,
                        totalCount = response.totalCount,
                        page = response.page,
                        limit = response.limit,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        threads = emptyList(),
                        errorMessage = error.message ?: error.error ?: "Failed to load threads",
                    )
                }
            }

        threadRepository.threadList(ThreadListRequest(1, 10, "owner"))
            .onSuccess { ownerResponse ->
                threadRepository.threadList(ThreadListRequest(1, 10, "member"))
                    .onSuccess { memberResponse ->
                        val myThreads = (ownerResponse.threads + memberResponse.threads)
                            .distinctBy { it.id }
                        val myThreadIds = myThreads.map { it.id }.toSet()

                        _state.update {
                            it.copy(
                                threads = it.threads.filterNot { thread -> thread.id in myThreadIds },
                                myThreads = myThreads,
                            )
                        }
                    }
                    .onError { error ->
                        val myThreads = ownerResponse.threads
                        val myThreadIds = myThreads.map { it.id }.toSet()

                        _state.update {
                            it.copy(
                                threads = it.threads.filterNot { thread -> thread.id in myThreadIds },
                                myThreads = myThreads,
                                errorMessage = error.message ?: error.error ?: "Failed to load member threads",
                            )
                        }
                    }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        myThreads = emptyList(),
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

    private fun joinThread(id: String) = viewModelScope.launch {
        threadRepository.joinThread(id)
            .onSuccess {
                _state.update { state ->
                    val joinedThread = state.threads.firstOrNull { it.id == id }

                    state.copy(
                        threads = state.threads.filterNot { it.id == id },
                        myThreads = if (joinedThread == null) {
                            state.myThreads
                        } else {
                            state.myThreads + joinedThread
                        },
                        errorMessage = null,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to join thread",
                    )
                }
            }
    }

    private fun leaveThread(id: String) = viewModelScope.launch {
        threadRepository.leaveThread(id)
            .onSuccess {
                _state.update { state ->
                    val leftThread = state.myThreads.firstOrNull { it.id == id }

                    state.copy(
                        myThreads = state.myThreads.filterNot { it.id == id },
                        threads = if (leftThread == null) {
                            state.threads
                        } else {
                            state.threads + leftThread.copy(
                                isMember = false,
                                role = null,
                                memberCount = (leftThread.memberCount - 1).coerceAtLeast(0),
                            )
                        },
                        errorMessage = null,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        errorMessage = error.message ?: error.error ?: "Failed to leave thread",
                    )
                }
            }
    }
}
