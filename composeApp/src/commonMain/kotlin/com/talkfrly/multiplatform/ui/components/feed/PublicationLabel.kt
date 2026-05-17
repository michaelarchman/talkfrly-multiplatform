package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.forum
import talkfrly_multiplatform.composeapp.generated.resources.icon_sms

enum class PublicationLabelType {
    THREAD_NAME, PUBLICATION_TYPE
}

@Composable
fun PublicationLabel(
    title: String,
    type: PublicationLabelType,
    onClick: (() -> Unit)? = null,
) {
    val chipIcon: ImageVector = when(type) {
        PublicationLabelType.THREAD_NAME -> vectorResource(Res.drawable.icon_sms)
        PublicationLabelType.PUBLICATION_TYPE -> vectorResource(Res.drawable.forum)
    }

    if (type == PublicationLabelType.PUBLICATION_TYPE) {
        val chipTitle: String = when (title) {
            "article" -> "Article"
            "ranking" -> "Ranking"
            "review" -> "Review"
            else -> "General"
        }

        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(LocalTalkfrlyColors.current.primary.copy(alpha = 0.1f))
                .border(1.dp, LocalTalkfrlyColors.current.primary20, RoundedCornerShape(4.dp))
                .padding(horizontal = 6.dp, vertical = 4.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                modifier = Modifier.size(10.dp),
                imageVector = chipIcon,
                contentDescription = "Feed label chip icon",
                tint = LocalTalkfrlyColors.current.body
            )

            Text(
                text = chipTitle,
                fontSize = 11.sp,
                color = LocalTalkfrlyColors.current.body
            )
        }
    }

    if (type == PublicationLabelType.THREAD_NAME) {
        val threadModifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(LocalTalkfrlyColors.current.accent)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 6.dp, vertical = 4.dp)

        Row(
            modifier = threadModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight(600),
                color = LocalTalkfrlyColors.current.background
            )
        }
    }
}