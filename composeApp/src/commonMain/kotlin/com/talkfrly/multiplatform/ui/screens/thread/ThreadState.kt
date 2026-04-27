package com.talkfrly.multiplatform.ui.screens.thread

import com.talkfrly.multiplatform.domain.simpleThread.SimpleThread

data class ThreadState(
    val threads: List<SimpleThread> = emptyList(),
    val totalCount: Int = 0,
    val page: Int = 1,
    val limit: Int = 0,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)
