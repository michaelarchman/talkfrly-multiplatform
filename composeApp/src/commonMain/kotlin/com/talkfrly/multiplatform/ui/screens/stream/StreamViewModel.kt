package com.talkfrly.multiplatform.ui.screens.stream

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.stream.repository.StreamRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StreamViewModel(
    private val streamRepository: StreamRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(StreamState())
    val state: StateFlow<StreamState> get() = _state

    private var streamId: String = ""

    fun initialize(streamId: String) {
        this.streamId = streamId
    }

    fun onIntent(intent: StreamIntent) {
        when (intent) {
            is StreamIntent.LoadStream -> loadStream()
            is StreamIntent.NavigateBack -> Unit
            is StreamIntent.SetComment -> setComment(intent.comment)
        }
    }

    private fun loadStream() = viewModelScope.launch {
        if (streamId.isBlank()) return@launch

        startLoading()
        _state.update { it.copy(isLoading = true) }

        streamRepository.getStreamById(streamId)
            .onSuccess { stream ->
                _state.update {
                    it.copy(
                        stream = stream,
                        isLoading = false,
                    )
                }
            }
            .onError {
                _state.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }
            .onFinally {
                stopLoading()
            }
    }

    private fun setComment(comment: String) {
        _state.update { it.copy(commentInput = comment) }
    }
}
