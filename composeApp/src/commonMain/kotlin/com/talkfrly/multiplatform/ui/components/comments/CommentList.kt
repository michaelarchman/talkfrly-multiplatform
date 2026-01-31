package com.talkfrly.multiplatform.ui.components.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun CommentList(
    comments: List<Comment>,
    isLoading: Boolean,
    onReply: (Comment) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Header
        Text(
            text = "Comments (${comments.size})",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = colors.body,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = colors.primary)
                }
            }
            comments.isEmpty() -> {
                Text(
                    text = "No comments yet. Be the first to comment!",
                    fontSize = 14.sp,
                    color = colors.bodyMuted,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    textAlign = TextAlign.Center,
                )
            }
            else -> {
                comments.forEach { comment ->
                    CommentItem(
                        comment = comment,
                        onReply = onReply,
                    )
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = colors.backgroundDarker,
                    )
                }
            }
        }
    }
}