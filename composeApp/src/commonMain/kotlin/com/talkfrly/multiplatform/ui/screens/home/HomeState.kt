package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse
import com.talkfrly.multiplatform.domain.user.User

data class HomeState(
    val currentUser: User? = null,
    val streams: List<StreamViewerResponse> = emptyList(),
    val selectedTabIndex: Int = 0,
    val isLoadingStreams: Boolean = false,
)