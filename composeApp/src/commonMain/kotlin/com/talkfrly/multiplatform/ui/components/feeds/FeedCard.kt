package com.talkfrly.multiplatform.ui.components.feeds

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
        shape = ShapeDefaults.ExtraSmall
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
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

                Text(
                    text = formatTimestamp(feedItem.createdAt),
                    color = colors.bodyMuted,
                    maxLines = 1,
                )
            }

            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = feedItem.content,
                    color = colors.body,
                )

                feedItem.imageUrls.firstOrNull()?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(model = imageUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().clip(ShapeDefaults.ExtraSmall),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            HorizontalDivider(color = colors.backgroundLighter)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FeedStat(
                        icon = Res.drawable.heart,
                        label = "${feedItem.voteScore}",
                    )
                    FeedStat(
                        icon = Res.drawable.icon_chat,
                        label = "${feedItem.commentCount}",
                    )
                }

                OutlinedButton(
                    onClick = {},
                    enabled = true,
                    border = BorderStroke(0.dp, Color.Transparent),
                    contentPadding = PaddingValues(horizontal = 6.dp),
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = vectorResource(Res.drawable.icon_visibility_on),
                        contentDescription = "feed view",
                        tint = colors.bodyMuted,
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = feedItem.views.toString(),
                        color = colors.bodyMuted,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
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
private fun FeedStat(
    icon: DrawableResource,
    label: String,
) {
    val colors = LocalTalkfrlyColors.current

    OutlinedButton(
        onClick = {},
        enabled = true,
        border = BorderStroke(1.dp, colors.primary20),
        contentPadding = PaddingValues(horizontal = 6.dp),
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = vectorResource(icon),
            contentDescription = "feed state $label",
            tint = colors.body,
        )
        Spacer(Modifier.width(4.dp))
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
