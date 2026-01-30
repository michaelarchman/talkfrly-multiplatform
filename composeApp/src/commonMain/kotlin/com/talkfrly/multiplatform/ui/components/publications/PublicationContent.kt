package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.domain.util.parseMarkdownSimple
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun PublicationContent(
    content: String,
    viewMode: PublicationViewMode,
    modifier: Modifier = Modifier,
    onSeeMoreClick: (() -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current
    val maxLines = if (viewMode == PublicationViewMode.FEED) 5 else Int.MAX_VALUE

    val isClipped = viewMode == PublicationViewMode.FEED && content.lines().size > maxLines

    Column(modifier = modifier) {
        Text(
            text = parseMarkdownSimple(content, linkColor = colors.primary),
            style = TextStyle(
                fontSize = 14.sp,
                color = colors.body,
            ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
        )

        if (isClipped && onSeeMoreClick != null) {
            Text(
                text = "See more",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = colors.primary,
                ),
                modifier = Modifier.clickable(onClick = onSeeMoreClick),
            )
        }
    }
}
