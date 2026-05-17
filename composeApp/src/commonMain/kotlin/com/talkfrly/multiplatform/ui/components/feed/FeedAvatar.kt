package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

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