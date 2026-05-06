package com.talkfrly.multiplatform.ui.screens.thread

import com.talkfrly.multiplatform.domain.thread.Thread

data class ThreadsState(
    val allThreads: List<Thread> = emptyList(),
    val joinedThreads: List<Thread> = emptyList(),
    val ownedThreads: List<Thread> = emptyList(),
    val selectedThreadFilter: ThreadFilter = ThreadFilter.Popular,
    val allThreadsTotalCount: Int = 0,
    val allThreadsPage: Int = 1,
    val allThreadsLimit: Int = 0,
    val joinedThreadsTotalCount: Int = 0,
    val joinedThreadsPage: Int = 1,
    val joinedThreadsLimit: Int = 0,
    val ownedThreadsTotalCount: Int = 0,
    val ownedThreadsPage: Int = 1,
    val ownedThreadsLimit: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

enum class ThreadFilter(
    val label: String,
) {
    Popular("Popular"),
    Owned("Owned"),
    Joined("Joined"),
}
