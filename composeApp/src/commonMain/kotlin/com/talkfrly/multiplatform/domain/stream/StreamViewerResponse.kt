package com.talkfrly.multiplatform.domain.stream

data class StreamViewerResponse(
    val id: String,
    val name: String,
    val ownerId: String,
    val displayName: String,
    val category: String,
    val avatarUrl: String? = null,
    val thumbnailUrl: String? = null,
    val isLive: Boolean,
    val playbackUrl: String? = null,
    val threadId: String? = null,
    val threadSlug: String? = null,
    val threadName: String? = null,
)