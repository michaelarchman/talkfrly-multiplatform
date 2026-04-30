package com.talkfrly.multiplatform.ui.screens.thread

import com.talkfrly.multiplatform.domain.thread.Thread

data class ThreadState(
    val threads: List<Thread> = emptyList(),
    val myThreads: List<Thread> = emptyList(),
    val totalCount: Int = 0,
    val page: Int = 1,
    val limit: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
