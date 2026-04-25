package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButton
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButtonType
import com.talkfrly.multiplatform.ui.screens.home.feed.FeedTabIntent
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.forum
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on
import talkfrly_multiplatform.composeapp.generated.resources.record_voice_over

@Composable
fun FeedCard(
    feedItem: FeedItem,
    modifier: Modifier = Modifier,
    onAction: (FeedTabIntent) -> Unit,
    onItemClick: (FeedItem) -> Unit,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = modifier
            .clickable(onClick = { onItemClick(feedItem) })
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
            contentColor = colors.body,
        ),
        shape = ShapeDefaults.ExtraSmall,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeedAvatar(
                        avatarUrl = feedItem.user.avatarUrl,
                        label = feedItem.user.displayName,
                    )

                    Text(
                        text = feedItem.user.displayName,
                        color = colors.body,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Text(
                    text = formatTimestamp(feedItem.createdAt),
                    color = colors.bodyMuted,
                    fontSize = 12.sp,
                    maxLines = 1,
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                var isOverflow by remember { mutableStateOf(false) }

                val lines = feedItem.content.lines()
                val headerLine = lines.firstOrNull { it.startsWith("#") }
                val bodyText = lines.firstOrNull { !it.startsWith("#") && it.isNotBlank() }.orEmpty()

                if (headerLine != null) {
                    Text(
                        text = headerLine.removePrefix("#").trimStart(),
                        color = colors.body,
                        fontSize = 18.sp,
                        letterSpacing = 0.8.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                if (bodyText.isNotBlank()) {
                    Text(
                        text = bodyText,
                        color = colors.body,
                        fontSize = 16.sp,
                        letterSpacing = 0.8.sp,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight.Light,
                        maxLines = 6,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { result ->
                            isOverflow = result.hasVisualOverflow
                        }
                    )
                }

                if (isOverflow) {
                    Text(
                        text = "Read more",
                        color = colors.primary,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                feedItem.imageUrls.let { url ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        url.forEach { image ->
                            Image(
                                painter = rememberAsyncImagePainter(model = image),
                                contentDescription = null,
                                alignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .heightIn(120.dp, 240.dp)
                                    .weight(1f)
                                    .clip(ShapeDefaults.ExtraSmall),
                                contentScale = ContentScale.Crop,
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    InteractionStatButton(
                        isActive = feedItem.likedByUser,
                        type = InteractionStatButtonType.OUTLINED,
                        icon = Res.drawable.record_voice_over,
                        label = feedItem.likeCount,
                        onClick = {},
                    )

                    InteractionStatButton(
                        isActive = false,
                        type = InteractionStatButtonType.OUTLINED,
                        icon = Res.drawable.forum,
                        label = feedItem.commentCount,
                        onClick = {}
                    )
                }

                InteractionStatButton(
                    type = InteractionStatButtonType.SIMPLE,
                    icon = Res.drawable.icon_visibility_on,
                    label = feedItem.views,
                    isActive = false,
                    onClick = {}
                )
            }
        }
    }
}

@Composable
fun FeedAvatar(
    avatarUrl: String?,
    label: String,
) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        modifier = Modifier.size(36.dp),
        shape = CircleShape,
        color = colors.backgroundDarker,
    ) {
        if (!avatarUrl.isNullOrBlank()) {
            Image(
                painter = rememberAsyncImagePainter(model = avatarUrl),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
                contentScale = ContentScale.Crop,
            )
        } else {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(colors.primary20),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label.firstOrNull()?.uppercase() ?: "?",
                    color = colors.body,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                )
            }
        }
    }
}

private fun formatTimestamp(createdAt: String): String {
    return try {
        createdAt.substringBefore("T")
    } catch (_: Exception) {
        createdAt
    }
}
