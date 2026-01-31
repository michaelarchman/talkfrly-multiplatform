package com.talkfrly.multiplatform.ui.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
            .background(colors.background)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            modifier = Modifier.weight(1f),
            singleLine = true,
            minLines = 1,
            maxLines = 1,
            shape = RoundedCornerShape(100),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.bodyMuted,
                focusedTextColor = colors.body,
                unfocusedTextColor = colors.body,
                cursorColor = colors.primary,
                focusedContainerColor = colors.backgroundLighter,
                unfocusedContainerColor = colors.backgroundLighter,
            ),
        )

        IconButton(
            onClick = onSendClick,
            modifier = Modifier
                .background(
                    color = if (isEnabled) colors.primary else colors.bodyMuted,
                    shape = CircleShape,
                )
                .height(48.dp),
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