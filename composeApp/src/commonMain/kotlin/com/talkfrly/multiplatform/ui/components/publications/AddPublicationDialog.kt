package com.talkfrly.multiplatform.ui.components.publications

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.talkfrly.multiplatform.domain.publication.PublicationType
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chat_paste_go
import talkfrly_multiplatform.composeapp.generated.resources.campaign
import talkfrly_multiplatform.composeapp.generated.resources.star
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat

enum class VisibilityOption {
    PUBLIC,
    THREAD_ONLY,
    PRIVATE
}

@Composable
fun AddPublicationDialog(
    selectedType: PublicationType,
    title: String,
    body: String,
    isSubmitting: Boolean,
    errorMessage: String? = null,
    isAnonymous: Boolean = false,
    visibility: VisibilityOption = VisibilityOption.PUBLIC,
    tags: List<String> = emptyList(),
    tagInput: String = "",
    inThreadContext: Boolean = false,
    postingAs: String = "Anonymous",
    onTypeSelected: (PublicationType) -> Unit,
    onTitleChanged: (String) -> Unit,
    onBodyChanged: (String) -> Unit,
    onAnonymousChanged: (Boolean) -> Unit,
    onVisibilityChanged: (VisibilityOption) -> Unit,
    onTagInputChanged: (String) -> Unit,
    onTagAdded: (String) -> Unit,
    onTagRemoved: (String) -> Unit,
    onDismiss: () -> Unit,
    onSubmit: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = LocalTalkfrlyColors.current.background
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title with icon
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = vectorResource(
                            when (selectedType) {
                                PublicationType.GENERAL -> Res.drawable.chat_paste_go
                                PublicationType.NEWS -> Res.drawable.campaign
                                PublicationType.REVIEW -> Res.drawable.star
                                PublicationType.RANKING -> Res.drawable.icon_chat
                            }
                        ),
                        contentDescription = null,
                        tint = LocalTalkfrlyColors.current.body,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "New ${selectedType.displayName}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = LocalTalkfrlyColors.current.body
                    )
                }

                // Type Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    PublicationType.entries.forEach { type ->
                        PublicationTypePill(
                            type = type,
                            isSelected = selectedType == type,
                            onClick = { onTypeSelected(type) },
                            enabled = !isSubmitting
                        )
                    }
                }

                // General Type Content
                when (selectedType) {
                    PublicationType.GENERAL -> {
                        GeneralPublicationForm(
                            title = title,
                            body = body,
                            isAnonymous = isAnonymous,
                            visibility = visibility,
                            tags = tags,
                            tagInput = tagInput,
                            inThreadContext = inThreadContext,
                            isSubmitting = isSubmitting,
                            onTitleChanged = onTitleChanged,
                            onBodyChanged = onBodyChanged,
                            onAnonymousChanged = onAnonymousChanged,
                            onVisibilityChanged = onVisibilityChanged,
                            onTagInputChanged = onTagInputChanged,
                            onTagAdded = onTagAdded,
                            onTagRemoved = onTagRemoved
                        )
                    }
                    else -> {
                        // Placeholder for other types
                        Text(
                            text = "${selectedType.displayName} form - Coming soon",
                            color = LocalTalkfrlyColors.current.bodyMuted,
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    }
                }

                // Error message
                errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Action buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = onDismiss,
                        enabled = !isSubmitting
                    ) {
                        Text(
                            text = "Anuluj",
                            color = LocalTalkfrlyColors.current.bodyMuted
                        )
                    }

                    TextButton(
                        onClick = onSubmit,
                        enabled = !isSubmitting && body.isNotBlank()
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp).padding(horizontal = 16.dp),
                                color = LocalTalkfrlyColors.current.primary
                            )
                        } else {
                            Text(
                                text = when (visibility) {
                                    VisibilityOption.PRIVATE -> "Publish Private"
                                    else -> "Publish Public"
                                },
                                color = if (body.isNotBlank())
                                    LocalTalkfrlyColors.current.primary
                                else
                                    LocalTalkfrlyColors.current.bodyMuted
                            )
                        }
                    }
                }

                // Footer - Posting as
                Text(
                    text = "Posting as: $postingAs",
                    fontSize = 12.sp,
                    color = LocalTalkfrlyColors.current.bodyMuted,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun PublicationTypePill(
    type: PublicationType,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) LocalTalkfrlyColors.current.primary
                else LocalTalkfrlyColors.current.surface
            )
            .border(
                width = 1.dp,
                color = if (isSelected) LocalTalkfrlyColors.current.primary
                else LocalTalkfrlyColors.current.bodyMuted.copy(alpha = 0.3f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = vectorResource(
                    when (type) {
                        PublicationType.GENERAL -> Res.drawable.chat_paste_go
                        PublicationType.NEWS -> Res.drawable.campaign
                        PublicationType.REVIEW -> Res.drawable.star
                        PublicationType.RANKING -> Res.drawable.icon_chat
                    }
                ),
                contentDescription = null,
                tint = if (isSelected) Color.White else LocalTalkfrlyColors.current.body,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = type.displayName,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                color = if (isSelected) Color.White else LocalTalkfrlyColors.current.body
            )
        }
    }
}

@Composable
private fun GeneralPublicationForm(
    title: String,
    body: String,
    isAnonymous: Boolean,
    visibility: VisibilityOption,
    tags: List<String>,
    tagInput: String,
    inThreadContext: Boolean,
    isSubmitting: Boolean,
    onTitleChanged: (String) -> Unit,
    onBodyChanged: (String) -> Unit,
    onAnonymousChanged: (Boolean) -> Unit,
    onVisibilityChanged: (VisibilityOption) -> Unit,
    onTagInputChanged: (String) -> Unit,
    onTagAdded: (String) -> Unit,
    onTagRemoved: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Visibility Section
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Visibility",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = LocalTalkfrlyColors.current.body
            )

            // Anonymous checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(enabled = !isSubmitting) { onAnonymousChanged(!isAnonymous) }
                    .padding(vertical = 4.dp)
            ) {
                androidx.compose.material3.Checkbox(
                    checked = isAnonymous,
                    onCheckedChange = onAnonymousChanged,
                    enabled = !isSubmitting,
                    colors = androidx.compose.material3.CheckboxDefaults.colors(
                        checkedColor = LocalTalkfrlyColors.current.primary
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Post anonymously",
                    fontSize = 14.sp,
                    color = LocalTalkfrlyColors.current.body
                )
            }

            // Visibility options
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                VisibilityRadioOption(
                    label = "Public",
                    description = "Visible to everyone",
                    isSelected = visibility == VisibilityOption.PUBLIC,
                    enabled = !isSubmitting,
                    onClick = { onVisibilityChanged(VisibilityOption.PUBLIC) }
                )

                if (inThreadContext) {
                    VisibilityRadioOption(
                        label = "Thread members only",
                        description = "Only visible to thread members",
                        isSelected = visibility == VisibilityOption.THREAD_ONLY,
                        enabled = !isSubmitting,
                        onClick = { onVisibilityChanged(VisibilityOption.THREAD_ONLY) }
                    )
                }

                VisibilityRadioOption(
                    label = "Private",
                    description = "Only visible to you",
                    isSelected = visibility == VisibilityOption.PRIVATE,
                    enabled = !isSubmitting,
                    onClick = { onVisibilityChanged(VisibilityOption.PRIVATE) }
                )
            }
        }

        // Title input
        TextField(
            value = title,
            onValueChange = onTitleChanged,
            label = { Text("Tytuł") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = LocalTalkfrlyColors.current.surface,
                unfocusedContainerColor = LocalTalkfrlyColors.current.surface,
                focusedTextColor = LocalTalkfrlyColors.current.body,
                unfocusedTextColor = LocalTalkfrlyColors.current.body,
                focusedLabelColor = LocalTalkfrlyColors.current.primary,
                unfocusedLabelColor = LocalTalkfrlyColors.current.bodyMuted,
            ),
            singleLine = true,
            enabled = !isSubmitting
        )

        // Body input with character counter
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextField(
                value = body,
                onValueChange = onBodyChanged,
                label = { Text("Treść") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LocalTalkfrlyColors.current.surface,
                    unfocusedContainerColor = LocalTalkfrlyColors.current.surface,
                    focusedTextColor = LocalTalkfrlyColors.current.body,
                    unfocusedTextColor = LocalTalkfrlyColors.current.body,
                    focusedLabelColor = LocalTalkfrlyColors.current.primary,
                    unfocusedLabelColor = LocalTalkfrlyColors.current.bodyMuted,
                ),
                maxLines = 8,
                enabled = !isSubmitting
            )
            Text(
                text = "${body.length} / 5000",
                fontSize = 12.sp,
                color = if (body.length > 5000) Color.Red else LocalTalkfrlyColors.current.bodyMuted,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Tags input
        TagsInput(
            tags = tags,
            tagInput = tagInput,
            onTagInputChanged = onTagInputChanged,
            onTagAdded = onTagAdded,
            onTagRemoved = onTagRemoved,
            enabled = !isSubmitting
        )
    }
}

@Composable
private fun VisibilityRadioOption(
    label: String,
    description: String,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = enabled) { onClick() }
            .padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            enabled = enabled,
            colors = RadioButtonDefaults.colors(
                selectedColor = LocalTalkfrlyColors.current.primary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = label,
                fontSize = 14.sp,
                color = LocalTalkfrlyColors.current.body
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
        }
    }
}

@Composable
private fun TagsInput(
    tags: List<String>,
    tagInput: String,
    onTagInputChanged: (String) -> Unit,
    onTagAdded: (String) -> Unit,
    onTagRemoved: (String) -> Unit,
    enabled: Boolean
) {
    val TAG_LIMIT = 6

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Tags (${tags.size}/$TAG_LIMIT)",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = LocalTalkfrlyColors.current.body
        )

        // Tags display + input
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = LocalTalkfrlyColors.current.bodyMuted.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display existing tags as chips
            tags.forEach { tag ->
                TagChip(
                    tag = tag,
                    onRemove = { onTagRemoved(tag) },
                    enabled = enabled
                )
            }

            // Input field for new tag
            if (tags.size < TAG_LIMIT) {
                TextField(
                    value = tagInput,
                    onValueChange = { newValue ->
                        // Handle Enter or comma to add tag
                        if (newValue.endsWith("\n") || newValue.endsWith(",")) {
                            val cleaned = newValue.trimEnd('\n', ',').trim()
                            if (cleaned.isNotEmpty()) {
                                onTagAdded(cleaned)
                            }
                        } else {
                            onTagInputChanged(newValue)
                        }
                    },
                    placeholder = {
                        Text(
                            "Add tag...",
                            fontSize = 13.sp,
                            color = LocalTalkfrlyColors.current.bodyMuted
                        )
                    },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = LocalTalkfrlyColors.current.body,
                        unfocusedTextColor = LocalTalkfrlyColors.current.body,
                    ),
                    singleLine = true,
                    enabled = enabled
                )
            }
        }

        if (tags.isNotEmpty()) {
            Text(
                text = "${tags.size} tag${if (tags.size != 1) "s" else ""} used (max $TAG_LIMIT)",
                fontSize = 11.sp,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
        }
    }
}

@Composable
private fun TagChip(
    tag: String,
    onRemove: () -> Unit,
    enabled: Boolean
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(LocalTalkfrlyColors.current.primary.copy(alpha = 0.1f))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = tag,
            fontSize = 13.sp,
            color = LocalTalkfrlyColors.current.body
        )
        Box(
            modifier = Modifier
                .size(16.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(enabled = enabled) { onRemove() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "×",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
        }
    }
}
