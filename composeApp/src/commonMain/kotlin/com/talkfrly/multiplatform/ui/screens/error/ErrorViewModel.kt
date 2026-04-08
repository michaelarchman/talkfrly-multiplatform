package com.talkfrly.multiplatform.ui.screens.error

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ErrorViewModel: ViewModel() {
    private val _state = MutableStateFlow(ErrorState())
    val state: StateFlow<ErrorState> get() = _state

    fun onIntent(intent: ErrorIntent){
        when(intent){
            ErrorIntent.onGoBack -> {}
        }
    }
}