package com.talkfrly.multiplatform.ui.screens.home.streams

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

class StreamsTabViewModel(
    private val streamRepository: StreamRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(StreamsTabState())
    val state: StateFlow<StreamsTabState> get() = _state

    fun onIntent(intent: StreamsTabIntent) {
        when (intent) {
            is StreamsTabIntent.GetStreams -> fetchCategories()
        }
    }

    private fun fetchCategories() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        streamRepository.getCategories()
            .onSuccess { result ->
                _state.update { it.copy(categories = result.items, isLoading = false) }
            }.onError {
                _state.update { it.copy(isLoading = false) }
            }.onFinally {
                _state.update { it.copy(isLoading = false) }
            }
    }
}