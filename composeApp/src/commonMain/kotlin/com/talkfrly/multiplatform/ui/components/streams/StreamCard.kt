package com.talkfrly.multiplatform.ui.components.streams

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun StreamCard(
    stream: StreamViewerResponse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = colors.backgroundLighter,
            contentColor = colors.body,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            stream.thumbnailUrl?.let { thumbnailUrl ->
                Image(
                    painter = rememberAsyncImagePainter(model = thumbnailUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            Column(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 2.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    StreamAvatar(
                        avatarUrl = stream.avatarUrl,
                        label = stream.name,
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Text(
                            text = stream.name,
                            color = colors.body,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Text(
                            text = stream.category,
                            color = colors.bodyMuted,
                            fontSize = 13.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    StreamStatusPill(isLive = stream.isLive)
                }

                if (stream.playbackUrl.isNullOrBlank()) {
                    Text(
                        text = "Playback URL unavailable",
                        color = colors.bodyMuted,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}

@Composable
private fun StreamAvatar(
    avatarUrl: String?,
    label: String,
) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        modifier = Modifier.size(40.dp),
        shape = CircleShape,
        color = colors.backgroundDarker,
    ) {
        if (!avatarUrl.isNullOrBlank()) {
            Image(
                painter = rememberAsyncImagePainter(model = avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(colors.primary20),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label.firstOrNull()?.uppercase() ?: "?",
                    color = colors.body,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }
        }
    }
}

@Composable
private fun StreamStatusPill(isLive: Boolean) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        shape = RoundedCornerShape(999.dp),
        color = if (isLive) colors.primary20 else colors.backgroundDarker,
    ) {
        Text(
            text = if (isLive) "LIVE" else "Offline",
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = if (isLive) colors.primary else colors.bodyMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
