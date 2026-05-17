package com.talkfrly.multiplatform.ui.screens.thread

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
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
    private val feedRepository: FeedRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ThreadState())
    val state: StateFlow<ThreadState> get() = _state

    fun onIntent(intent: ThreadIntent) {
        when (intent) {
            is ThreadIntent.GetThreads -> getThreads()
            is ThreadIntent.GetJoinedThreads -> getJoinedThreads()
            is ThreadIntent.GetOwnedThreads -> getOwnedThreads()
            is ThreadIntent.GetThreadById -> getThreadById(intent.id)
            is ThreadIntent.GetPublicationsForThread -> getPublicationsForThread(intent.threadId)
            is ThreadIntent.JoinThread -> joinThread(intent.id)
            is ThreadIntent.LeaveThread -> leaveThread(intent.id)
            is ThreadIntent.SetFilter -> _state.update { it.copy(filter = intent.filter) }
            is ThreadIntent.SetSearchQuery -> _state.update { it.copy(searchQuery = intent.query, searchResults = if (intent.query.isEmpty()) emptyList() else it.searchResults) }
            is ThreadIntent.SearchThreads -> searchThreads(intent.query)
        }
    }

    private fun searchThreads(q: String) = viewModelScope.launch {
        if (q.isBlank()) {
            _state.update { it.copy(searchResults = emptyList()) }
            return@launch
        }
        startLoading()
        threadRepository.searchThreads(q, 1, 20)
            .onSuccess { response ->
                _state.update { it.copy(searchResults = response.threads) }
            }
            .onError { error ->
                _state.update { it.copy(errorMessage = error.message ?: error.error ?: "Search failed") }
            }
            .onFinally { stopLoading() }
    }

    private fun getPublicationsForThread(threadId: String) = viewModelScope.launch {
        startLoading()
        feedRepository.getThreadFeed(threadId, 1, 20)
            .onSuccess { feed ->
                _state.update { it.copy(publications = feed.publications) }
            }
            .onError { error ->
                _state.update { it.copy(errorMessage = error.message ?: error.error ?: "Failed to load feed") }
            }
            .onFinally { stopLoading() }
    }

    private fun getThreadById(id: String) = viewModelScope.launch {
        startLoading()
        threadRepository.getThreadById(id)
            .onSuccess { thread ->
                _state.update { it.copy(currentThread = thread) }
            }
            .onError { error ->
                _state.update {
                    it.copy(errorMessage = error.message ?: error.error ?: "Failed to load thread")
                }
            }
            .onFinally { stopLoading() }
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