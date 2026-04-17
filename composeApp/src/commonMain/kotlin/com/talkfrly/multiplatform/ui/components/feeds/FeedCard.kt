package com.talkfrly.multiplatform.ui.components.feeds

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.heart
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on

@Composable
fun FeedCard(
    feedItem: FeedItem,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.backgroundLighter,
            contentColor = colors.body,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FeedAvatar(
                        avatarUrl = feedItem.user.avatarUrl,
                        label = feedItem.user.displayName,
                    )

                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            if (feedItem.isPrivate) {
                                FeedPill(
                                    label = "private",
                                    highlighted = false,
                                )
                            }
                        }

                        Text(
                            text = "Posted by ${feedItem.user.displayName} • ${formatTimestamp(feedItem.createdAt)}",
                            color = colors.bodyMuted,
                            fontSize = 12.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }


                Text(
                    text = feedItem.content,
                    color = colors.body,
                    fontSize = 15.sp,
                    lineHeight = 21.sp,
                )

                feedItem.imageUrls.firstOrNull()?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .border(
                                width = 1.dp,
                                color = colors.backgroundDarker,
                                shape = RoundedCornerShape(14.dp),
                            ),
                        contentScale = ContentScale.Crop,
                    )
                }

                HorizontalDivider(color = colors.backgroundDarker)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    FeedStat(
                        icon = Res.drawable.heart,
                        label = "${feedItem.voteScore}",
                    )
                    FeedStat(
                        icon = Res.drawable.icon_chat,
                        label = "${feedItem.commentCount}",
                    )
                    FeedStat(
                        icon = Res.drawable.icon_visibility_on,
                        label = "${feedItem.views}",
                    )
                }
            }
        }
    }
}

@Composable
private fun FeedAvatar(
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

@Composable
private fun FeedPill(
    label: String,
    highlighted: Boolean = true,
) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        shape = RoundedCornerShape(999.dp),
        color = if (highlighted) colors.primary20 else colors.backgroundDarker,
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            color = if (highlighted) colors.body else colors.bodyMuted,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

@Composable
private fun FeedStat(
    icon: DrawableResource,
    label: String,
) {
    val colors = LocalTalkfrlyColors.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.height(12.dp),
            imageVector = vectorResource(icon),
            contentDescription = null,
            tint = colors.bodyMuted,
        )
        Text(
            text = label,
            color = colors.bodyMuted,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}


private fun formatTimestamp(createdAt: String): String {
    return try {
        createdAt.substringBefore("T")
    } catch (_: Exception) {
        createdAt
    }
}
