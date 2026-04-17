package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.domain.feed.Feed
import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

data class HomeState(
    val message: String = "Welcome to Home!",
    val feeds: Feed? = null,
    val streams: List<StreamViewerResponse> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isLoadingStreams: Boolean = false,
)