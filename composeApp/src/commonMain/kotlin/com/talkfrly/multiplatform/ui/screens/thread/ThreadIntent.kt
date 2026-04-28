package com.talkfrly.multiplatform.ui.screens.thread

sealed interface ThreadIntent {
    data object GetThreads : ThreadIntent
}
