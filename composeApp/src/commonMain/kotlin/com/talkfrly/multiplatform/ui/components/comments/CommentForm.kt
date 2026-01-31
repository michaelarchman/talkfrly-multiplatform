package com.talkfrly.multiplatform.ui.components.comments

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.components.buttons.ButtonPrimary
import com.talkfrly.multiplatform.ui.components.buttons.ButtonSizeType
import com.talkfrly.multiplatform.ui.components.inputs.CheckboxLabeled
import com.talkfrly.multiplatform.ui.components.inputs.TextArea
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors

data class CommentFormState(
    val content: String = "",
    val isAnonymous: Boolean = false,
    val isSubmitting: Boolean = false,
)

@Composable
fun CommentForm(
    state: CommentFormState,
    onContentChange: (String) -> Unit,
    onAnonymousChange: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    onCancel: (() -> Unit)? = null,
    placeholder: String = "Write a comment...",
    modifier: Modifier = Modifier,
) {
    val colors = LocalTalkfrlyColors.current
    val isSubmitEnabled = state.content.trim().isNotEmpty() && !state.isSubmitting

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface,
        ),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // TextArea
            TextArea(
                value = state.content,
                onValueChange = onContentChange,
                placeholder = placeholder,
                maxLength = 1000,
                minHeight = if (onCancel == null) 80.dp else 60.dp,
                showCharCount = true,
            )

            // Anonymous checkbox
            CheckboxLabeled(
                checked = state.isAnonymous,
                onCheckedChange = onAnonymousChange,
                label = "Post anonymously",
            )

            // Submit button
            if (onCancel != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ButtonPrimary(
                        text = "Cancel",
                        size = ButtonSizeType.SMALL,
                        enabled = !state.isSubmitting,
                        onClick = onCancel,
                        modifier = Modifier.fillMaxWidth(fraction = 0.5f),
                    )
                    ButtonPrimary(
                        text = if (state.isSubmitting) "Posting..." else "Post Comment",
                        size = ButtonSizeType.SMALL,
                        enabled = isSubmitEnabled,
                        onClick = onSubmit,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    ButtonPrimary(
                        text = if (state.isSubmitting) "Posting..." else "Post Comment",
                        size = ButtonSizeType.SMALL,
                        enabled = isSubmitEnabled,
                        onClick = onSubmit,
                    )
                }
            }
        }
    }
}