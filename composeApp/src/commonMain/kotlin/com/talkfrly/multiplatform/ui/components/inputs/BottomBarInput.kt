package com.talkfrly.multiplatform.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add
import talkfrly_multiplatform.composeapp.generated.resources.chat_paste_go

@Composable
fun BottomBarInput(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit,
    placeholder: String = "Write a comment...",
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current
    val isEnabled = value.trim().isNotEmpty()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.backgroundLighter)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val interactionSource = remember { MutableInteractionSource() }

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.weight(1f),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(color = colors.body),
            cursorBrush = SolidColor(colors.primary),
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colors.backgroundLighter,
                            shape = RoundedCornerShape(100)
                        )
                        .border(
                            width = 1.dp,
                            color = colors.bodyMuted,
                            shape = RoundedCornerShape(100)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = LocalTextStyle.current,
                            color = colors.bodyMuted
                        )
                    }
                    innerTextField()
                }
            }
        )

        IconButton(
            onClick = onSendClick,
            modifier = Modifier
                .background(
                    color = if (isEnabled) colors.primary else colors.bodyMuted,
                    shape = CircleShape,
                )
                .height(40.dp),
            enabled = isEnabled,
        ) {
            Icon(
                imageVector = if (isEnabled) vectorResource(Res.drawable.chat_paste_go) else vectorResource(Res.drawable.add),
                contentDescription = "Send",
                tint = if (isEnabled) colors.surface else colors.background,
            )
        }
    }
}