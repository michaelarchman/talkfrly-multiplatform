package com.talkfrly.multiplatform.ui.components.inputs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

@Composable
fun TextArea(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String? = null,
    maxLength: Int = 1000,
    minHeight: Dp = 80.dp,
    isError: Boolean = false,
    errorMessage: String? = null,
    showCharCount: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current

    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            placeholder = {
                placeholder?.let { Text(it, color = colors.bodyMuted) }
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = minHeight),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colors.primary,
                unfocusedBorderColor = colors.primary20,
                focusedContainerColor = colors.backgroundLighter,
                unfocusedContainerColor = colors.backgroundDarker,
                cursorColor = colors.body,
                focusedTextColor = colors.body,
                unfocusedTextColor = colors.body,
                errorBorderColor = colors.error,
            ),
            isError = isError,
            minLines = 3,
            maxLines = 10,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isError && errorMessage != null) {
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = colors.error,
                )
            } else {
                Spacer(modifier = Modifier)
            }

            if (showCharCount) {
                Text(
                    text = "${value.length}/$maxLength",
                    fontSize = 12.sp,
                    color = if (value.length >= maxLength) colors.error else colors.bodyMuted,
                )
            }
        }
    }
}