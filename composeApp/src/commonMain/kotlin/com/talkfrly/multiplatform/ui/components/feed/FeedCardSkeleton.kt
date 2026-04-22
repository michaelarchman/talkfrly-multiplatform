package com.talkfrly.multiplatform.ui.components.feed

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun FeedCardSkeleton(modifier: Modifier = Modifier) {
    val colors = LocalTalkfrlyColors.current

    val transition = rememberInfiniteTransition()
    val alpha by transition.animateFloat(
        initialValue = 0.3f,
        targetValue = 0.7f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800),
            repeatMode = RepeatMode.Reverse,
        )
    )

    val shimmerColor = colors.backgroundLighter.copy(alpha = alpha)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        shape = ShapeDefaults.ExtraSmall,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // Header: avatar + name + date
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(shimmerColor)
                    )
                    Box(
                        modifier = Modifier
                            .width(120.dp)
                            .height(14.dp)
                            .clip(ShapeDefaults.ExtraSmall)
                            .background(shimmerColor)
                    )
                }
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(12.dp)
                        .clip(ShapeDefaults.ExtraSmall)
                        .background(shimmerColor)
                )
            }

            // Content lines
            Column(
                modifier = Modifier.padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(4) { index ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(if (index == 3) 0.6f else 1f)
                            .height(16.dp)
                            .clip(ShapeDefaults.ExtraSmall)
                            .background(shimmerColor)
                    )
                }
            }

            // Footer: stats
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                repeat(2) {
                    Box(
                        modifier = Modifier
                            .width(56.dp)
                            .height(32.dp)
                            .clip(ShapeDefaults.ExtraSmall)
                            .background(shimmerColor)
                    )
                }
            }
        }
    }
}
