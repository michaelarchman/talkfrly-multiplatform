package com.talkfrly.multiplatform

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AppViewModel(

): BaseViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state: StateFlow<AppState> = _state
}