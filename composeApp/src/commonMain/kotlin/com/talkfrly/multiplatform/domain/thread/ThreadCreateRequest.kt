package com.talkfrly.multiplatform.domain.thread

data class ThreadCreateRequest(
    val name: String,
    val slug: String,
    val description: String? = null,
)
