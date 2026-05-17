package com.talkfrly.multiplatform.ui.screens.thread

import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.domain.thread.Thread

data class ThreadState(
    val currentThread: Thread? = null,
    val publications: List<FeedItem> = emptyList(),

    val allThreads: List<Thread> = emptyList(),
    val allThreadsTotal: Int = 0,
    val allThreadsPage: Int = 1,
    val allThreadsLimit: Int = 0,

    val joinedThreads: List<Thread> = emptyList(),
    val ownedThreads: List<Thread> = emptyList(),
    val totalCount: Int = 0,
    val page: Int = 1,
    val limit: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    val filter: ThreadFilter = ThreadFilter.ALL,
)
