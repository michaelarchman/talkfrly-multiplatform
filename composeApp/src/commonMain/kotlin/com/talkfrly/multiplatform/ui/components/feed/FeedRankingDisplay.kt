package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.domain.ranking.Ranking
import com.talkfrly.multiplatform.domain.ranking.RankedItem
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun FeedRankingDisplay(
    ranking: Ranking,
    modifier: Modifier = Modifier,
    onVote: ((itemId: String, value: Int) -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current
    val sorted = ranking.items.sortedWith(compareByDescending<RankedItem> { it.score }.thenBy { it.name.lowercase() })
    val maxAbsScore = remember(sorted) { sorted.maxOfOrNull { kotlin.math.abs(it.score) }?.coerceAtLeast(1) ?: 1 }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.backgroundLighter, RoundedCornerShape(8.dp))
            .border(1.dp, colors.primary20, RoundedCornerShape(8.dp))
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "Ranking",
            color = colors.body,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        if (sorted.isEmpty()) {
            Text(
                text = "No items in this ranking yet.",
                color = colors.bodyMuted,
                fontSize = 14.sp,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        } else {
            var votingItemId by remember(ranking.id, ranking.updatedAt) { mutableStateOf<String?>(null) }

            sorted.forEachIndexed { index, item ->
                RankingItem(
                    rank = index + 1,
                    item = item,
                    maxAbsScore = maxAbsScore,
                    voting = votingItemId != null,
                    onVote = if (onVote == null) {
                        null
                    } else { nextValue ->
                        votingItemId = item.id
                        try {
                            onVote(item.id, nextValue)
                        } finally {
                            votingItemId = null
                        }
                    },
                )
            }
        }
    }
}

@Composable
private fun RankingItem(
    rank: Int,
    item: RankedItem,
    maxAbsScore: Int,
    voting: Boolean,
    onVote: ((Int) -> Unit)?,
) {
    val colors = LocalTalkfrlyColors.current
    val currentVote = item.userVote
    val backgroundBrush = remember(item.score, maxAbsScore, colors.background, colors.error20, colors.primary20) {
        rankingItemBackground(
            score = item.score,
            maxAbsScore = maxAbsScore,
            backgroundColor = colors.background,
            positiveColor = colors.primary20,
            negativeColor = colors.error20,
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundBrush, RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = if (currentVote != null) colors.primary20 else colors.backgroundDarker,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .background(colors.primary, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = rank.toString(),
                color = colors.black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Text(
                text = item.name,
                color = colors.body,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
        }

        if (onVote != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                VoteButton(
                    label = "+",
                    active = currentVote == 1,
                    enabled = !voting,
                    isNegative = false,
                    onClick = {
                        onVote(if (currentVote == 1) 0 else 1)
                    },
                )
                VoteButton(
                    label = "\u2212",
                    active = currentVote == -1,
                    enabled = !voting,
                    isNegative = true,
                    onClick = {
                        onVote(if (currentVote == -1) 0 else -1)
                    },
                )
            }
        }
    }
}

private fun rankingItemBackground(
    score: Int,
    maxAbsScore: Int,
    backgroundColor: Color,
    positiveColor: Color,
    negativeColor: Color,
): Brush {
    val fill = (kotlin.math.abs(score).toFloat() / maxAbsScore.toFloat()).coerceIn(0f, 1f)
    if (fill == 0f) {
        return Brush.horizontalGradient(listOf(backgroundColor, backgroundColor))
    }
    val tint = if (score < 0) negativeColor else positiveColor

    return Brush.horizontalGradient(
        colorStops = arrayOf(
            0f to tint,
            fill to backgroundColor,
            1f to backgroundColor,
        )
    )
}

@Composable
private fun VoteButton(
    label: String,
    active: Boolean,
    enabled: Boolean,
    isNegative: Boolean,
    onClick: () -> Unit,
) {
    val colors = LocalTalkfrlyColors.current

    Surface(
        modifier = Modifier
            .size(26.dp)
            .alpha(if (enabled) 1f else 0.5f)
            .clickable(enabled = enabled, onClick = onClick),
        color = if (isNegative) colors.backgroundDarker else colors.primary,
        contentColor = if (isNegative) colors.body else colors.black,
        shape = RoundedCornerShape(8.dp),
        shadowElevation = if (active) 2.dp else 0.dp,
        tonalElevation = 0.dp,
        border = if (active) androidx.compose.foundation.BorderStroke(2.dp, colors.bodyMuted) else null,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = label,
                fontSize = 18.sp,
                fontWeight = if (active) FontWeight.Black else FontWeight.Bold,
            )
        }
    }
}
