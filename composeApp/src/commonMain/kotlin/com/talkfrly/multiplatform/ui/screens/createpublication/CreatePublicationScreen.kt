package com.talkfrly.multiplatform.ui.screens.createpublication

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.domain.publication.PublicationType
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_a_photo
import talkfrly_multiplatform.composeapp.generated.resources.add_picture
import talkfrly_multiplatform.composeapp.generated.resources.campaign
import talkfrly_multiplatform.composeapp.generated.resources.chat_paste_go
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat
import talkfrly_multiplatform.composeapp.generated.resources.star

@Composable
fun CreatePublicationScreenRoot(
    navController: NavController,
    threadId: String? = null,
    threadName: String? = null,
    viewModel: CreatePublicationViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Initialize with thread context if provided
    androidx.compose.runtime.LaunchedEffect(threadId, threadName) {
        viewModel.initialize(threadId, threadName)
    }

    CreatePublicationScreen(
        state = state,
        onIntent = { intent ->
            viewModel.onIntent(intent)
            if (intent is CreatePublicationIntent.NavigateBack) {
                navController.popBackStack()
            }
            if (intent is CreatePublicationIntent.Submit && !state.isSubmitting && state.error == null) {
                // Wait a bit to ensure the API call succeeds before navigating
                navController.popBackStack()
            }
        },
        onNavigateBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreatePublicationScreen(
    state: CreatePublicationState,
    onIntent: (CreatePublicationIntent) -> Unit,
    onNavigateBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = vectorResource(
                                when (state.selectedType) {
                                    PublicationType.GENERAL -> Res.drawable.chat_paste_go
                                    PublicationType.NEWS -> Res.drawable.campaign
                                    PublicationType.REVIEW -> Res.drawable.star
                                    PublicationType.RANKING -> Res.drawable.icon_chat
                                }
                            ),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Text("New ${state.selectedType.displayName}")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
                )
            )
        },
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        contentWindowInsets = WindowInsets.systemBars
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            // Type TabRow
            SecondaryScrollableTabRow(
                selectedTabIndex = PublicationType.entries.indexOf(state.selectedType),
                containerColor = LocalTalkfrlyColors.current.background,
                contentColor = LocalTalkfrlyColors.current.body,
                edgePadding = 16.dp,
                indicator = {
                    Box(
                        modifier = Modifier
                            .tabIndicatorOffset(
                                PublicationType.entries.indexOf(state.selectedType),
                                matchContentSize = false
                            )
                            .height(2.dp)
                            .background(LocalTalkfrlyColors.current.primary)
                    )
                },
                divider = {},
                modifier = Modifier.height(48.dp),
            ) {
                PublicationType.entries.forEach { type ->
                    Tab(
                        selectedContentColor = LocalTalkfrlyColors.current.body,
                        unselectedContentColor = LocalTalkfrlyColors.current.bodyMuted,
                        selected = state.selectedType == type,
                        onClick = { onIntent(CreatePublicationIntent.SetType(type)) },
                        enabled = !state.isSubmitting,
                        modifier = Modifier.height(48.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp)
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
                                modifier = Modifier.size(18.dp).padding(end = 4.dp)
                            )
                            Text(
                                text = type.displayName,
                                fontWeight = FontWeight(600)
                            )
                        }
                    }
                }
            }

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Content based on selected type
                when (state.selectedType) {
                    PublicationType.GENERAL -> {
                        GeneralPublicationForm(
                            state = state,
                            onIntent = onIntent
                        )
                    }
                    else -> {
                        Text(
                            text = "${state.selectedType.displayName} form - Coming soon",
                            color = LocalTalkfrlyColors.current.bodyMuted,
                            modifier = Modifier.padding(vertical = 32.dp)
                        )
                    }
                }

                // Error message
                state.error?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Submit button
                Button(
                    onClick = { onIntent(CreatePublicationIntent.Submit) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !state.isSubmitting && state.content.isNotBlank(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = LocalTalkfrlyColors.current.primary
                    )
                ) {
                    if (state.isSubmitting) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = when (state.visibility) {
                                VisibilityOption.PRIVATE -> "Publish Private"
                                else -> "Publish Public"
                            }
                        )
                    }
                }

                // Footer
                Text(
                    text = "Posting as: ${if (state.isAnonymous) "Anonymous" else "You"}",
                    fontSize = 12.sp,
                    color = LocalTalkfrlyColors.current.bodyMuted
                )
            }
        }
    }
}

@Composable
private fun GeneralPublicationForm(
    state: CreatePublicationState,
    onIntent: (CreatePublicationIntent) -> Unit
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
                    .clickable(enabled = !state.isSubmitting) {
                        onIntent(CreatePublicationIntent.SetAnonymous(!state.isAnonymous))
                    }
                    .padding(vertical = 4.dp)
            ) {
                Checkbox(
                    checked = state.isAnonymous,
                    onCheckedChange = { onIntent(CreatePublicationIntent.SetAnonymous(it)) },
                    enabled = !state.isSubmitting,
                    colors = CheckboxDefaults.colors(
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VisibilityRadioOption(
                        label = "Public",
                        description = "Visible to everyone",
                        isSelected = state.visibility == VisibilityOption.PUBLIC,
                        enabled = !state.isSubmitting,
                        onClick = { onIntent(CreatePublicationIntent.SetVisibility(VisibilityOption.PUBLIC)) },
                        modifier = Modifier.weight(1f)
                    )
                    VisibilityRadioOption(
                        label = "Private",
                        description = "Only visible to you",
                        isSelected = state.visibility == VisibilityOption.PRIVATE,
                        enabled = !state.isSubmitting,
                        onClick = { onIntent(CreatePublicationIntent.SetVisibility(VisibilityOption.PRIVATE)) },
                        modifier = Modifier.weight(1f)
                    )
                }

                if (state.inThreadContext) {
                    VisibilityRadioOption(
                        label = "Thread members only",
                        description = "Only visible to thread members",
                        isSelected = state.visibility == VisibilityOption.THREAD_ONLY,
                        enabled = !state.isSubmitting,
                        onClick = { onIntent(CreatePublicationIntent.SetVisibility(VisibilityOption.THREAD_ONLY)) }
                    )
                }
            }
        }

        // Photos
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Photos",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = LocalTalkfrlyColors.current.body
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PhotoActionButton(
                    label = "Camera",
                    icon = Res.drawable.add_a_photo,
                    enabled = !state.isSubmitting,
                    onClick = { onIntent(CreatePublicationIntent.OpenCamera) }
                )
                PhotoActionButton(
                    label = "Gallery",
                    icon = Res.drawable.add_picture,
                    enabled = !state.isSubmitting,
                    onClick = { onIntent(CreatePublicationIntent.OpenGallery) }
                )
            }

            if (state.imageUris.isNotEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    state.imageUris.forEachIndexed { index, uri ->
                        ImageChip(
                            label = "Photo ${index + 1}",
                            enabled = !state.isSubmitting,
                            onRemove = { onIntent(CreatePublicationIntent.RemoveImage(uri)) }
                        )
                    }
                }
            }
        }

        // Content input with character counter
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            TextField(
                value = state.content,
                onValueChange = { onIntent(CreatePublicationIntent.SetContent(it)) },
                label = { Text("Write something") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = LocalTalkfrlyColors.current.backgroundLighter,
                    unfocusedContainerColor = LocalTalkfrlyColors.current.backgroundLighter,
                    focusedTextColor = LocalTalkfrlyColors.current.body,
                    unfocusedTextColor = LocalTalkfrlyColors.current.body,
                    focusedLabelColor = LocalTalkfrlyColors.current.body,
                    unfocusedLabelColor = LocalTalkfrlyColors.current.bodyMuted,
                    unfocusedIndicatorColor = LocalTalkfrlyColors.current.primary20,
                    focusedIndicatorColor = LocalTalkfrlyColors.current.primary,
                    cursorColor = LocalTalkfrlyColors.current.surface,
                ),
                maxLines = 10,
                enabled = !state.isSubmitting
            )
            Text(
                text = "${state.content.length} / 5000",
                fontSize = 12.sp,
                color = if (state.content.length > 5000) Color.Red else LocalTalkfrlyColors.current.bodyMuted,
                modifier = Modifier.fillMaxWidth()
            )
        }

        // Tags input
        TagsInput(
            tags = state.tags,
            tagInput = state.tagInput,
            onTagInputChanged = { onIntent(CreatePublicationIntent.SetTagInput(it)) },
            onTagAdded = { onIntent(CreatePublicationIntent.AddTag(it)) },
            onTagRemoved = { onIntent(CreatePublicationIntent.RemoveTag(it)) },
            enabled = !state.isSubmitting
        )
    }
}

@Composable
private fun VisibilityRadioOption(
    label: String,
    description: String,
    isSelected: Boolean,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
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

@Composable
private fun PhotoActionButton(
    label: String,
    icon: org.jetbrains.compose.resources.DrawableResource,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(enabled = enabled) { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = vectorResource(icon),
            contentDescription = label,
            tint = LocalTalkfrlyColors.current.body
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 13.sp,
            color = LocalTalkfrlyColors.current.body
        )
    }
}

@Composable
private fun ImageChip(
    label: String,
    enabled: Boolean,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(LocalTalkfrlyColors.current.primary.copy(alpha = 0.1f))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
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
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = LocalTalkfrlyColors.current.bodyMuted
            )
        }
    }
}
