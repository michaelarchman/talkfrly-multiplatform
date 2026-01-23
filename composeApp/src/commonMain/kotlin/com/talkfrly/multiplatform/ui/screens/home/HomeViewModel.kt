package com.talkfrly.multiplatform.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel() : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Logout -> { }
            is HomeIntent.GetPublications -> getPublications()
        }
    }

    private fun getPublications() = viewModelScope.launch {
        startLoading()

        stopLoading()
    }
}