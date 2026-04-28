package com.talkfrly.multiplatform.ui.components.chips

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.talkfrly.multiplatform.data.uploads.ImageUploadStatus
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun ImageChip(
    label: String,
    thumbnail: String,
    status: ImageUploadStatus,
    errorText: String?,
    enabled: Boolean,
    onRemove: () -> Unit,
    onRetry: () -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(LocalTalkfrlyColors.current.primary.copy(alpha = 0.1f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            AsyncImage(
                model = thumbnail,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(6.dp)),
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = LocalTalkfrlyColors.current.body
            )
            when (status) {
                ImageUploadStatus.UPLOADING -> Text(
                    text = "Uploading",
                    fontSize = 11.sp,
                    color = LocalTalkfrlyColors.current.bodyMuted
                )
                ImageUploadStatus.SUCCESS -> Text(
                    text = "Ready",
                    fontSize = 11.sp,
                    color = LocalTalkfrlyColors.current.primary
                )
                ImageUploadStatus.ERROR -> Text(
                    text = "Failed",
                    fontSize = 11.sp,
                    color = Color.Red,
                    modifier = Modifier.clickable(enabled = enabled) { onRetry() }
                )
                ImageUploadStatus.PENDING -> {}
            }
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(enabled = enabled) { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "×",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = LocalTalkfrlyColors.current.bodyMuted
                )
            }
        }
        if (status == ImageUploadStatus.ERROR && !errorText.isNullOrBlank()) {
            Text(
                text = errorText,
                fontSize = 11.sp,
                color = Color.Red,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}