package com.talkfrly.multiplatform.ui.screens.stream

import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

data class StreamState(
    val stream: StreamViewerResponse? = null,
    val isLoading: Boolean = false,
    val isLoadingComments: Boolean = false,
    val commentInput: String = "",
)
