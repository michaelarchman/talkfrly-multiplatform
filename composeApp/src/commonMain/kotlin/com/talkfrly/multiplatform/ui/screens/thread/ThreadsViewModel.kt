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

class ThreadsViewModel(
    private val threadRepository: ThreadRepository,
) : BaseViewModel() {
    private companion object {
        const val THREADS_LIMIT = 7
    }

    private val _state = MutableStateFlow(ThreadsState())
    val state: StateFlow<ThreadsState> get() = _state

    fun onIntent(intent: ThreadsIntent) {
        when (intent) {
            is ThreadsIntent.GetThreads -> getThreads()
            is ThreadsIntent.GetJoinedThreads -> getJoinedThreads()
            is ThreadsIntent.GetOwnedThreads -> getOwnedThreads()
            is ThreadsIntent.SelectThreadFilter -> selectThreadFilter(intent.filter)
            is ThreadsIntent.ChangeThreadPage -> changeThreadPage(intent.filter, intent.page)
            is ThreadsIntent.JoinThread -> joinThread(intent.id)
            is ThreadsIntent.LeaveThread -> leaveThread(intent.id)

        }
    }

    private fun selectThreadFilter(filter: ThreadFilter) {
        _state.update {
            it.copy(selectedThreadFilter = filter)
        }
    }

    private fun changeThreadPage(filter: ThreadFilter, page: Int) {
        when (filter) {
            ThreadFilter.Popular -> getThreads(page)
            ThreadFilter.Owned -> getOwnedThreads(page)
            ThreadFilter.Joined -> getJoinedThreads(page)
        }
    }

    private fun getThreads(page: Int = _state.value.allThreadsPage) = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(page, THREADS_LIMIT, ""))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        allThreads = response.threads,
                        allThreadsTotalCount = response.totalCount,
                        allThreadsPage = response.page,
                        allThreadsLimit = response.limit,
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
                getThreads(_state.value.allThreadsPage)
                getJoinedThreads(_state.value.joinedThreadsPage)
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
                getThreads(_state.value.allThreadsPage)
                getJoinedThreads(_state.value.joinedThreadsPage)
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

    private fun getJoinedThreads(page: Int = _state.value.joinedThreadsPage) = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(page, THREADS_LIMIT, "member"))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        joinedThreads = response.threads,
                        joinedThreadsTotalCount = response.totalCount,
                        joinedThreadsPage = response.page,
                        joinedThreadsLimit = response.limit,
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

    private fun getOwnedThreads(page: Int = _state.value.ownedThreadsPage) = viewModelScope.launch {
        startLoading()

        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null,
            )
        }

        threadRepository.threadList(ThreadListRequest(page, THREADS_LIMIT, "owner"))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        ownedThreads = response.threads,
                        ownedThreadsTotalCount = response.totalCount,
                        ownedThreadsPage = response.page,
                        ownedThreadsLimit = response.limit,
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