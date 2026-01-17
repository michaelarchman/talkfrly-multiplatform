package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.ui.session.SessionViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val sessionViewModel: SessionViewModel
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Logout -> {
                sessionViewModel.logout()
            }
        }
    }
}