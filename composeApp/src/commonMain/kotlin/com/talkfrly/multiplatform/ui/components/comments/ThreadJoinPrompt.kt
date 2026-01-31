package com.talkfrly.multiplatform.ui.components.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat

@Composable
fun ThreadJoinPrompt(
    threadName: String,
    isJoining: Boolean,
    onJoin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.primary20,
        ),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = vectorResource(Res.drawable.icon_chat),
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = colors.primary,
            )

            Text(
                text = "Join \"$threadName\" to comment",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = colors.body,
                textAlign = TextAlign.Center,
            )

            Text(
                text = "This is a members-only thread. Join to participate in the conversation.",
                fontSize = 14.sp,
                color = colors.bodyMuted,
                textAlign = TextAlign.Center,
            )

            ButtonPrimary(
                text = if (isJoining) "Joining..." else "Join Thread",
                size = ButtonSizeType.MEDIUM,
                enabled = !isJoining,
                onClick = onJoin,
            )
        }
    }
}