package com.talkfrly.multiplatform.ui.components.streams

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
actual fun StreamVideoPlayer(
    playbackUrl: String?,
    modifier: Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (playbackUrl.isNullOrBlank()) {
                "Stream is offline."
            } else {
                "Native iOS player not implemented yet."
            },
            color = LocalTalkfrlyColors.current.bodyMuted,
        )
    }
}
