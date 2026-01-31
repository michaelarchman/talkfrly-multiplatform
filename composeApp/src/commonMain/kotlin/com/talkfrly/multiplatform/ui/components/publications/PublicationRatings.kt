package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.domain.publication.CriterionSummary
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import kotlin.math.round
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.star

@Composable
fun PublicationRatings(
    criteria: List<CriterionSummary>?,
    modifier: Modifier = Modifier,
) {
    if (criteria.isNullOrEmpty()) {
        return
    }

    val colors = LocalTalkfrlyColors.current
    val starIcon = vectorResource(Res.drawable.star)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        criteria.forEach { criterion ->
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = criterion.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.body,
                    modifier = Modifier.weight(1f),
                )

                // Display stars (out of 10)
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    repeat(5) { index ->
                        val starFilled = (index + 1) <= (criterion.average / 2).toInt()
                        Icon(
                            imageVector = starIcon,
                            contentDescription = null,
                            tint = if (starFilled) colors.primary else colors.bodyMuted,
                        )
                    }
                }

                Text(
                    text = (round(criterion.average * 10) / 10).toString(),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.body,
                )
            }
        }
    }
}
