package com.talkfrly.multiplatform.domain.stream

data class StreamList(
    val items: List<StreamViewerResponse>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)
