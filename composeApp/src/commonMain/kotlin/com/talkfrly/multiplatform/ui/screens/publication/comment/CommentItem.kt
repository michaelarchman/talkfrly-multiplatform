package com.talkfrly.multiplatform.ui.screens.publication.comment

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
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
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.ui.components.feed.FeedAvatar
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import com.talkfrly.multiplatform.ui.utils.formatRelativeTime
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.more_vert

@Composable
fun CommentItem(comment: Comment, onOptionClick: () -> Unit) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            FeedAvatar(
                avatarUrl = comment.user?.avatarUrl,
                label = comment.user?.displayName ?: "Anonymous",
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text = comment.user?.displayName ?: "Anonymous",
                        color = LocalTalkfrlyColors.current.body,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(
                            text = formatRelativeTime(comment.createdAt),
                            color = LocalTalkfrlyColors.current.bodyMuted,
                            fontSize = 14.sp,
                        )

                        Icon(
                            modifier = Modifier
                                .size(16.dp)
                                .clickable(onClick = onOptionClick),
                            imageVector = vectorResource(Res.drawable.more_vert),
                            contentDescription = null,
                            tint = LocalTalkfrlyColors.current.bodyMuted,
                        )
                    }
                }

                Text(
                    text = comment.content,
                    color = LocalTalkfrlyColors.current.body,
                )

                if (comment.imageUrls.isNotEmpty()) {
                    Image(
                        modifier = Modifier.clip(ShapeDefaults.ExtraSmall),
                        painter = rememberAsyncImagePainter(model = comment.imageUrls.first()),
                        contentDescription = "Comment picture",
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 8.dp),
            thickness = 1.dp,
            color = LocalTalkfrlyColors.current.backgroundLighter,
        )
    }
}