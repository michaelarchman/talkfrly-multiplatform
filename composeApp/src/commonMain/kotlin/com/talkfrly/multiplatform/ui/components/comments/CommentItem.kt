package com.talkfrly.multiplatform.ui.components.comments

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.util.getYouTubeThumbnail
import com.talkfrly.multiplatform.domain.util.parseMarkdownSimple
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat

@Composable
fun CommentItem(
    comment: Comment,
    onReply: (Comment) -> Unit,
    isReply: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current
    val displayName = if (comment.isAnonymous) "Anonymous"
    else (comment.user?.displayName ?: "Unknown")

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = if (isReply) 40.dp else 0.dp,
            ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        // Header (avatar + name + timestamp)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar (32dp circular)
            Surface(
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                color = colors.backgroundLighter,
            ) {
                if (!comment.avatarUrl.isNullOrBlank()) {
                    Image(
                        painter = rememberAsyncImagePainter(model = comment.avatarUrl),
                        contentDescription = "Avatar for $displayName",
                        modifier = Modifier.size(32.dp),
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(colors.primary20),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = displayName.firstOrNull()?.uppercase() ?: "?",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colors.body,
                        )
                    }
                }
            }

            Text(
                text = displayName,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.body,
            )

            if (comment.isAnonymous) {
                Surface(
                    modifier = Modifier.size(height = 16.dp, width = 60.dp),
                    color = colors.primary20,
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Anonymous",
                            fontSize = 10.sp,
                            color = colors.primary,
                        )
                    }
                }
            }

            Text(
                text = formatTimestamp(comment.createdAt),
                fontSize = 12.sp,
                color = colors.bodyMuted,
            )
        }

        // Content (markdown rendered)
        Text(
            text = parseMarkdownSimple(comment.content, linkColor = colors.primary),
            fontSize = 14.sp,
            color = colors.body,
            modifier = Modifier.padding(start = 44.dp),
        )

        // Media (images)
        if (comment.imageUrls.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(start = 44.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(comment.imageUrls) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "Comment image",
                        modifier = Modifier
                            .size(height = 120.dp, width = 160.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }

        // YouTube thumbnail
        if (!comment.videoEmbedUrl.isNullOrBlank() && comment.videoId != null) {
            val thumbnailUrl = getYouTubeThumbnail(comment.videoId)
            AsyncImage(
                model = thumbnailUrl,
                contentDescription = "YouTube video",
                modifier = Modifier
                    .padding(start = 44.dp)
                    .size(height = 120.dp, width = 160.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )
        }

        // Reply button (only for top-level comments)
        if (!isReply) {
            TextButton(
                onClick = { onReply(comment) },
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primary20
                ),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.icon_chat),
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = colors.body,
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = "Reply",
                    fontSize = 9.sp,
                    color = colors.bodyMuted,
                )
            }
        }

        // Replies
        if (comment.replies.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                comment.replies.forEach { reply ->
                    CommentItem(
                        comment = reply,
                        onReply = { },
                        isReply = true,
                    )
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: String): String {
    return try {
        val parts = timestamp.split("T")
        if (parts.isNotEmpty()) parts[0] else timestamp
    } catch (_: Exception) {
        timestamp
    }
}