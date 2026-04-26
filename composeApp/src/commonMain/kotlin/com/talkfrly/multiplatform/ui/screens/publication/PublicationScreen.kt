package com.talkfrly.multiplatform.ui.screens.publication

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.rememberAsyncImagePainter
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest
import com.talkfrly.multiplatform.ui.components.bars.BottomBarInput
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButton
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButtonType
import com.talkfrly.multiplatform.ui.components.feed.FeedAvatar
import com.talkfrly.multiplatform.ui.screens.createpublication.CreatePublicationIntent
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import com.talkfrly.multiplatform.ui.utils.formatRelativeTime
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.chat_paste_go
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.delete
import talkfrly_multiplatform.composeapp.generated.resources.icon_chat
import talkfrly_multiplatform.composeapp.generated.resources.icon_visibility_on
import talkfrly_multiplatform.composeapp.generated.resources.more_vert
import talkfrly_multiplatform.composeapp.generated.resources.person
import talkfrly_multiplatform.composeapp.generated.resources.record_voice_over
import talkfrly_multiplatform.composeapp.generated.resources.report

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PublicationScreenRoot(
    publicationId: String,
    viewModel: PublicationScreenViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()
    val loadingCount by viewModel.loadingCount.collectAsState()
    val scope = rememberCoroutineScope()

    val publicationDrawerState = rememberDrawerState(DrawerValue.Closed)
    val commentSheetState = rememberModalBottomSheetState()
    var selectedCommentToBottomSheet by remember { mutableStateOf<Comment?>(null) }

    val isOwner = state.currentUser?.id == publicationId

    LaunchedEffect(Unit) {
        viewModel.onIntent(PublicationScreenIntent.GetCurrentUser)
        viewModel.onIntent(PublicationScreenIntent.GetPublications(publicationId))
        viewModel.onIntent(PublicationScreenIntent.GetComments(publicationId))
    }

    ModalNavigationDrawer(
        drawerState = publicationDrawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = LocalTalkfrlyColors.current.background,
                drawerContentColor = LocalTalkfrlyColors.current.body,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp).padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Text(
                            text = "Publication options",
                            color = LocalTalkfrlyColors.current.body,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.report),
                            contentDescription = "Back",
                            tint = LocalTalkfrlyColors.current.bodyMuted,
                        )

                        Text(
                            text = "Report publication",
                            color = LocalTalkfrlyColors.current.body,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.person),
                            contentDescription = "Back",
                            tint = LocalTalkfrlyColors.current.bodyMuted,
                        )

                        Text(
                            text = "Block publications from ${state.publication?.user?.displayName}",
                            color = LocalTalkfrlyColors.current.body,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }

                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.report),
                            contentDescription = "Back",
                            tint = LocalTalkfrlyColors.current.bodyMuted,
                        )

                        Text(
                            text = "Report publication",
                            color = LocalTalkfrlyColors.current.body,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
            }
        }
    ) {
        selectedCommentToBottomSheet?.let {
            ModalBottomSheet(
                onDismissRequest = { selectedCommentToBottomSheet = null },
                sheetState = commentSheetState,
                containerColor = LocalTalkfrlyColors.current.background,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Comment options",
                            color = LocalTalkfrlyColors.current.body,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                        )

                        Text(
                            text = "Commented by ${selectedCommentToBottomSheet?.user?.displayName}",
                            color = LocalTalkfrlyColors.current.bodyMuted,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }


                    Row(
                        modifier = Modifier.clickable {
                            /* report */
                            scope.launch { commentSheetState.hide()
                            selectedCommentToBottomSheet = null } },
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.report),
                            contentDescription = null,
                            tint = LocalTalkfrlyColors.current.bodyMuted
                        )

                        Text("Report comment", color = LocalTalkfrlyColors.current.body)
                    }
                    Row(
                        modifier = Modifier.clickable {
                            /* delete */
                            scope.launch { commentSheetState.hide()
                            selectedCommentToBottomSheet = null } },
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.delete),
                            contentDescription = null,
                            tint = LocalTalkfrlyColors.current.bodyMuted
                        )

                        Text("Delete comment", color = LocalTalkfrlyColors.current.body)
                    }
                }
            }
        }

        Scaffold(
            containerColor = LocalTalkfrlyColors.current.background,
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            state.publication?.user?.let {
                                FeedAvatar(
                                    avatarUrl = state.publication!!.avatarUrl,
                                    label = it.displayName ?: "Anonymous",
                                )

                                Text(
                                    text = it.displayName ?: "Anonymous",
                                    color = LocalTalkfrlyColors.current.body,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { navController.popBackStack() }
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.chevron_left),
                                contentDescription = "Back",
                                tint = LocalTalkfrlyColors.current.body,
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = LocalTalkfrlyColors.current.background,
                        titleContentColor = LocalTalkfrlyColors.current.body,
                        navigationIconContentColor = LocalTalkfrlyColors.current.body,
                    ),
                    actions = {
                        OutlinedButton(
                            onClick = { scope.launch { publicationDrawerState.open() } },
                            enabled = true,
                            border = BorderStroke(1.dp, LocalTalkfrlyColors.current.primary20),
                            contentPadding = PaddingValues(horizontal = 6.dp),
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = vectorResource(Res.drawable.more_vert),
                                contentDescription = "Show more",
                                tint = LocalTalkfrlyColors.current.body,
                            )
                        }
                    }
                )
            },
            bottomBar = {
                TextField(
                    value = state.newCommentContent,
                    onValueChange = { viewModel.onIntent(PublicationScreenIntent.SetNewCommentContent(it)) },
                    label = { Text("Write something") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(60.dp, 160.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LocalTalkfrlyColors.current.backgroundLighter,
                        unfocusedContainerColor = LocalTalkfrlyColors.current.backgroundLighter,
                        disabledContainerColor = LocalTalkfrlyColors.current.backgroundLighter,
                        focusedTextColor = LocalTalkfrlyColors.current.body,
                        unfocusedTextColor = LocalTalkfrlyColors.current.body,
                        disabledTextColor = LocalTalkfrlyColors.current.body,
                        focusedLabelColor = LocalTalkfrlyColors.current.body,
                        unfocusedLabelColor = LocalTalkfrlyColors.current.bodyMuted,
                        disabledLabelColor = LocalTalkfrlyColors.current.bodyMuted,
                        unfocusedIndicatorColor = LocalTalkfrlyColors.current.primary20,
                        focusedIndicatorColor = LocalTalkfrlyColors.current.primary,
                        disabledIndicatorColor = LocalTalkfrlyColors.current.primary20,
                        cursorColor = LocalTalkfrlyColors.current.surface,
                    ),
                    maxLines = 10,
                    leadingIcon = { Icon(
                            vectorResource(Res.drawable.icon_chat),
                            "Comment")},
                    trailingIcon = {
                        Column(
                            Modifier.padding(end = 8.dp)
                        ) {
                            InteractionStatButton(
                                type = InteractionStatButtonType.OUTLINED,
                                isActive = false,
                                icon = Res.drawable.chat_paste_go,
                                onClick = { viewModel.onIntent(PublicationScreenIntent.PostComment(
                                    CreateCommentRequest(
                                        publicationId = publicationId,
                                        content = state.newCommentContent,
                                    )
                                ))}
                            )
                        }
                    }
                )
            }
        ) {
            LazyColumn(
                Modifier.padding(it)
            ) {
                item {
                    PublicationScreen(
                        state = state,
                        isLoading = loadingCount > 0,
                        onAction = { intent -> viewModel.onIntent(intent)},
                        onCommentOptionClick = { comment -> selectedCommentToBottomSheet = comment }
                    )
                }
            }
        }
    }
}

@Composable
private fun PublicationScreen(
    state: PublicationScreenState,
    isLoading: Boolean,
    onAction: (PublicationScreenIntent) -> Unit,
    onCommentOptionClick: (Comment) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        /**
         * Publication content
         */
        if (state.publication == null) {
            if (isLoading) {
                SplashScreen()
            } else {
                Text(
                    text = "No publication loaded. Try again.",
                    color = LocalTalkfrlyColors.current.body,
                )
            }
            return
        }

        state.publication.content.let { pub ->
            val lines = pub.lines()
            val headerLine = lines.firstOrNull { it.startsWith("#") }
            val bodyText = lines.firstOrNull { !it.startsWith("#") && it.isNotBlank() }.orEmpty()

            if (headerLine != null) {
                Text(
                    text = headerLine.removePrefix("#").trimStart(),
                    color = LocalTalkfrlyColors.current.body,
                    fontSize = 18.sp,
                    letterSpacing = 0.8.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (bodyText.isNotBlank()) {
                Text(
                    text = bodyText,
                    color = LocalTalkfrlyColors.current.body,
                    fontSize = 16.sp,
                    letterSpacing = 0.8.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight.Light,
                )
            }
        }

        state.publication.imageUrls.let {
            Image(
                modifier = Modifier.clip(ShapeDefaults.ExtraSmall),
                painter = rememberAsyncImagePainter(model = it.firstOrNull()),
                contentDescription = "Publication picture",
                contentScale = ContentScale.FillWidth,
            )
        }

        /**
         * Interaction buttons
         */
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            InteractionStatButton(
                isActive = state.publication.likedByUser,
                type = InteractionStatButtonType.OUTLINED,
                label = state.publication.likeCount,
                icon = Res.drawable.record_voice_over,
                onClick = {
                    onAction(
                        if (state.publication.likedByUser) PublicationScreenIntent.UnlikePublication(state.publication.id)
                        else PublicationScreenIntent.LikePublication(state.publication.id)
                    )
                }
            )

            InteractionStatButton(
                type = InteractionStatButtonType.SIMPLE,
                icon = Res.drawable.icon_visibility_on,
                label = state.publication.views,
                isActive = false,
            )
        }

        /**
         * Comments
         */
        state.comments?.let {
            Text(
                text = "Comments (${state.publication.commentCount})",
                color = LocalTalkfrlyColors.current.body,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
            )

            HorizontalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp),
                color = LocalTalkfrlyColors.current.primary20,
            )

            if (state.comments.isEmpty()) {
                Text(
                    text = "No comments yet. Be first!",
                    color = LocalTalkfrlyColors.current.bodyMuted,
                )
            }

            state.comments.forEach { comment ->
                Column {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FeedAvatar(
                            avatarUrl = comment.user?.avatarUrl,
                            label = comment.user?.displayName ?: "Anonymous",
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = comment.user?.displayName ?: "Anonymous",
                                    color = LocalTalkfrlyColors.current.body,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = formatRelativeTime(comment.createdAt),
                                        color = LocalTalkfrlyColors.current.bodyMuted,
                                        fontSize = 14.sp,
                                    )

                                    Icon(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clickable(onClick = { onCommentOptionClick(comment) }),
                                        imageVector = vectorResource(Res.drawable.more_vert),
                                        contentDescription = "feed view",
                                        tint = LocalTalkfrlyColors.current.bodyMuted,
                                    )
                                }
                            }

                            Text(
                                text = comment.content,
                                color = LocalTalkfrlyColors.current.body,
                            )
                        }
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.height(1.dp),
                    color = LocalTalkfrlyColors.current.backgroundLighter
                )
            }
        }
    }
}
