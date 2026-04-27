package com.talkfrly.multiplatform.domain.simpleThread

data class Thread2ListRequest(
    val page: Int,
    val limit: Int,
    val role: String,
)

data class Thread2ListResponse(
    val threads: List<SimpleThread>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)