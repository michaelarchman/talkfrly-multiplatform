package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

data class HomeState(
    val streams: List<StreamViewerResponse> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isLoadingStreams: Boolean = false,
)