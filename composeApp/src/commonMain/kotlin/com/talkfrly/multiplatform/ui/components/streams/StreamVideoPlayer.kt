package com.talkfrly.multiplatform.ui.components.streams

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun StreamVideoPlayer(
    playbackUrl: String?,
    modifier: Modifier = Modifier,
)
