package com.talkfrly.multiplatform.ui.screens.thread

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.simpleThread.repository.Thread2Repository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.simpleThread.Thread2ListRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreadViewModel(
    private val threadRepository: Thread2Repository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ThreadState())
    val state: StateFlow<ThreadState> get() = _state

    fun onIntent(intent: ThreadIntent) {
        when (intent) {
            ThreadIntent.GetThreads -> getThreads()
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

        threadRepository.getThreads(Thread2ListRequest(1,10,""))
            .onSuccess { response ->
                _state.update {
                    it.copy(
                        threads = response.threads,
                        totalCount = response.totalCount,
                        page = response.page,
                        limit = response.limit,
                        isLoading = false,
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        threads = emptyList(),
                        errorMessage = error.message ?: error.error ?: "Failed to load threads",
                    )
                }
            }
            .onFinally {
                stopLoading()
            }
    }
}