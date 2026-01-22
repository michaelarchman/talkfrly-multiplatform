package com.talkfrly.multiplatform

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel: ViewModel() {
    companion object {
        private val _globalLoadingCount = MutableStateFlow(0)
        val globalLoadingCount: StateFlow<Int> = _globalLoadingCount.asStateFlow()
    }

    private val _loadingCount = MutableStateFlow(0)

    protected fun startLoading() {
        println("Loading started loadingCount++")
        _loadingCount.update { it + 1 }
        _globalLoadingCount.update { it + 1 }
    }

    protected fun stopLoading() {
        println("Loading stopped loadingCount--")
        _loadingCount.update { maxOf(0, it - 1) }
        _globalLoadingCount.update { maxOf(0, it - 1) }
    }
}
