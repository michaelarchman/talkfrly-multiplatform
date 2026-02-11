package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun PublicationHeader(
    publication: Publication,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current
    val displayName = when {
        publication.isAnonymous -> "Anonymous"
        !publication.pseudonym.isNullOrBlank() -> publication.pseudonym
        !publication.user?.displayName.isNullOrBlank() -> publication.user.displayName
        else -> "Unknown"
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.Top,
    ) {
        // Avatar
        Surface(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            color = colors.backgroundLighter,
        ) {
            if (!publication.avatarUrl.isNullOrBlank()) {
                Image(
                    painter = rememberAsyncImagePainter(model = publication.avatarUrl),
                    contentDescription = "Avatar for $displayName",
                    modifier = Modifier.size(40.dp),
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(colors.primary20),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = displayName.firstOrNull()?.uppercase() ?: "?",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.body,
                    )
                }
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = displayName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.body,
                )

                // Badges
                publication.articleCategory?.let {
                    PublicationBadge(label = it, color = LocalTalkfrlyColors.current.bodyMuted)
                }
                if (!publication.threadName.isNullOrBlank()) {
                    PublicationBadge(label = publication.threadName)
                }
            }

            Text(
                text = formatTimestamp(publication.createdAt),
                fontSize = 12.sp,
                color = colors.bodyMuted,
            )
        }
    }
}

@Composable
private fun PublicationBadge(
    label: String,
    color: Color? = LocalTalkfrlyColors.current.primary60,
    modifier: Modifier = Modifier,
) {
    if (color != null) {
        Surface(
            modifier = modifier
                .height(20.dp)
                .padding(horizontal = 6.dp),
            color = color,
            shape = RoundedCornerShape(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 6.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LocalTalkfrlyColors.current.black,
                )
            }
        }
    }
}


private fun formatTimestamp(createdAt: String): String {
    // Simple formatting - just show the date part for now
    // In a real app, you might use expect/actual for platform-specific relative time
    return try {
        val parts = createdAt.split("T")
        if (parts.isNotEmpty()) parts[0] else createdAt
    } catch (e: Exception) {
        createdAt
    }
}
