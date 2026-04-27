package com.talkfrly.multiplatform.domain.simpleThread

data class SimpleThreadCreateRequest(
    val name: String,
    val slug: String,
    val description: String? = null,
)
