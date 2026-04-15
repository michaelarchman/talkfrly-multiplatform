package com.talkfrly.multiplatform.domain.thread

data class UpdateThreadRequest(
    val name: String,
    val description: String? = null,
)
