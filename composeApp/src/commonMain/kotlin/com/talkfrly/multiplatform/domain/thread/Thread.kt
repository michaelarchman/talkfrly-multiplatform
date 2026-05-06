package com.talkfrly.multiplatform.domain.thread

data class Thread(
    val id: String,
    val name: String,
    val slug: String,
    val description: String? = null,
    val creatorId: String,
    val memberCount: Int,
    val role: String? = null,
    val streamId: String? = null,
    val isLive: Boolean = false,
    val createdAt: String,
    val updatedAt: String,
)