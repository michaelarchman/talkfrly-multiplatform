package com.talkfrly.multiplatform

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AppViewModel(

) {
    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state
}