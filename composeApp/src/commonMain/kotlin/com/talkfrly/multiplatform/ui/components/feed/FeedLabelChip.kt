package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.forum
import talkfrly_multiplatform.composeapp.generated.resources.siren_open

enum class PublicationLabelChip {
    ARTICLE, REVIEW, RANKING,
}

enum class PublicationMembershipChip {
    ARTICLE, REVIEW, RANKING,
}

@Composable
fun PublicationLabel(
    title: String,
    type: PublicationLabelChip,
    membership: PublicationMembershipChip
) {
    val membershipIcon: ImageVector? = when (membership) {
        PublicationMembershipChip.RANKING -> vectorResource(Res.drawable.forum)
        PublicationMembershipChip.REVIEW -> vectorResource(Res.drawable.forum)
        PublicationMembershipChip.ARTICLE -> vectorResource(Res.drawable.siren_open)
    }

    Column {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(LocalTalkfrlyColors.current.primary.copy(alpha = 0.1f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            membershipIcon?.let {
                Icon(imageVector = it, contentDescription = "Feed label chip icon")
            }
            Text(
                text = title,
                fontSize = 12.sp,
                color = LocalTalkfrlyColors.current.body
            )
        }
    }
}