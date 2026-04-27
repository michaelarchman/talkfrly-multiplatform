package com.talkfrly.multiplatform.domain.simpleThread

data class SimpleThreadUpdateRequest(
    val name: String,
    val description: String? = null,
)
