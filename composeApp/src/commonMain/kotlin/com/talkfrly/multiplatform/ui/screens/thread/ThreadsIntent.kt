package com.talkfrly.multiplatform.ui.screens.thread

sealed class ThreadsIntent {
    data object GetThreads : ThreadsIntent()
    data object GetJoinedThreads : ThreadsIntent()
    data object GetOwnedThreads : ThreadsIntent()
    data class SelectThreadFilter(val filter: ThreadFilter) : ThreadsIntent()
    data class ChangeThreadPage(val filter: ThreadFilter, val page: Int) : ThreadsIntent()
    data class JoinThread(val id: String) : ThreadsIntent()
    data class LeaveThread(val id: String) : ThreadsIntent()
}
