package com.talkfrly.multiplatform.ui.screens.home.streams

import com.talkfrly.multiplatform.domain.stream.StreamCategory

data class StreamsTabState(
    val categories: List<StreamCategory> = emptyList(),
    val isLoading: Boolean = true,
)