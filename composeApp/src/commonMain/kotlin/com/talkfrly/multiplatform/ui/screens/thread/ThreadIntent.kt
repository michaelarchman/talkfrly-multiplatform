package com.talkfrly.multiplatform.ui.screens.thread

sealed class ThreadIntent {
    data object GetThreads : ThreadIntent()
    data object GetJoinedThreads : ThreadIntent()
    data object GetOwnedThreads : ThreadIntent()
    data class JoinThread(val id: String) : ThreadIntent()
    data class LeaveThread(val id: String) : ThreadIntent()
}
