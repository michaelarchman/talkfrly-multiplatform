package com.talkfrly.multiplatform.ui.screens.thread

enum class ThreadFilter { ALL, MINE, MEMBER }

sealed class ThreadIntent {
    data object GetThreads : ThreadIntent()
    data object GetJoinedThreads : ThreadIntent()
    data object GetOwnedThreads : ThreadIntent()
    data class GetThreadById(val id: String) : ThreadIntent()
    data class GetPublicationsForThread(val threadId: String) : ThreadIntent()
    data class JoinThread(val id: String) : ThreadIntent()
    data class LeaveThread(val id: String) : ThreadIntent()
    data class SetFilter(val filter: ThreadFilter) : ThreadIntent()
    data class SetSearchQuery(val query: String) : ThreadIntent()
    data class SearchThreads(val query: String) : ThreadIntent()
}
