package com.talkfrly.multiplatform.domain.stream

data class StreamViewerResponse(
    val id: String,
    val name: String,
    val ownerId: String,
    val category: String,
    val avatarUrl: String? = null,
    val isLive: Boolean,
    val playbackUrl: String? = null,
)
