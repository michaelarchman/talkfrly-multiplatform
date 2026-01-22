package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel() : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Logout -> { }
        }
    }
}