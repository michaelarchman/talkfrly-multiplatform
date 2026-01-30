package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.heart
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on

@Composable
fun PublicationFooter(
    publication: Publication,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    androidx.compose.foundation.layout.Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        // Tags
        if (publication.tags.isNotEmpty()) {
            LazyRow(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                items(publication.tags) { tag ->
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = tag,
                                fontSize = 12.sp,
                            )
                        },
                        modifier = Modifier.height(28.dp),
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = colors.background,
                            labelColor = colors.primary60,
                        ),
                        border = BorderStroke(0.dp, Color.Transparent),
                    )
                }
            }
        }

        // Stats row
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatItem(
                icon = Res.drawable.icon_visibility_on,
                label = "${publication.views ?: 0}",
            )
            StatItem(
                icon = Res.drawable.heart,
                label = "${publication.voteScore}",
            )
            StatItem(
                icon = Res.drawable.icon_chat,
                label = "${publication.commentCount}",
            )
        }
    }
}

@Composable
private fun StatItem(
    icon: org.jetbrains.compose.resources.DrawableResource,
    label: String,
) {
    val colors = LocalTalkfrlyColors.current

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = null,
            modifier = Modifier.width(16.dp),
            tint = colors.bodyMuted,
        )
        Text(
            text = label,
            fontSize = 12.sp,
            color = colors.bodyMuted,
            fontWeight = FontWeight.Medium,
        )
    }
}
