package com.talkfrly.multiplatform.domain.thread

data class ThreadListRequest(
    val page: Int,
    val limit: Int,
    val role: String,
)

data class ThreadListResponse(
    val threads: List<Thread>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)
