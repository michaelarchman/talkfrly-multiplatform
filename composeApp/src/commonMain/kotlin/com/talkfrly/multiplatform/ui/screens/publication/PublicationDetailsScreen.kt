package com.talkfrly.multiplatform.ui.screens.publication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.ui.components.comments.CommentForm
import com.talkfrly.multiplatform.ui.components.comments.CommentFormState
import com.talkfrly.multiplatform.ui.components.comments.CommentList
import com.talkfrly.multiplatform.ui.components.comments.ThreadJoinPrompt
import com.talkfrly.multiplatform.ui.components.bars.BottomBarInput
import com.talkfrly.multiplatform.ui.components.publications.PublicationCard
import com.talkfrly.multiplatform.ui.components.publications.PublicationViewMode
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left

@Composable
fun PublicationDetailsScreenRoot(
    viewModel: PublicationDetailsViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(PublicationDetailsIntent.GetPublicationDetails)
    }

    PublicationDetailsScreen(
        state = state,
        navController = navController,
        onAction = { intent ->
            when (intent) {
                PublicationDetailsIntent.NavigateBack -> navController.popBackStack()
                else -> viewModel.onIntent(intent)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PublicationDetailsScreen(
    state: PublicationDetailsState,
    navController: NavController,
    onAction: (PublicationDetailsIntent) -> Unit,
) {
    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        topBar = {
            TopAppBar(
                title = {
                    Text("Publication")
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(PublicationDetailsIntent.NavigateBack) }) {
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
            )
        },
        bottomBar = {
            BottomBarInput(
                value = state.commentFormContent,
                onValueChange = { onAction(PublicationDetailsIntent.UpdateCommentFormContent(it)) },
                onSendClick = { onAction(PublicationDetailsIntent.SubmitComment) },
                placeholder = "Write a comment...",
            )
        }
    ) { paddingValues ->
        when {
            state.publication != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        PublicationCard(
                            publication = state.publication,
                            viewMode = PublicationViewMode.DETAILS,
                            onClick = null,
                        )
                    }

                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            val canComment = (state.publication.threadMembersOnly != true)
                                || state.publication.isThreadMember

                            if (state.publication.threadMembersOnly == true && !state.publication.isThreadMember) {
                                ThreadJoinPrompt(
                                    threadName = state.publication.threadName ?: "this thread",
                                    isJoining = state.isJoiningThread,
                                    onJoin = { onAction(PublicationDetailsIntent.JoinThread) },
                                )
                            }

                            if (state.replyingTo != null) {
                                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                    Text(
                                        text = "Replying to ${if (state.replyingTo.isAnonymous) "Anonymous" else (state.replyingTo.user?.displayName ?: "Unknown")}",
                                        fontSize = 12.sp,
                                        color = LocalTalkfrlyColors.current.bodyMuted,
                                    )
                                    CommentForm(
                                        state = CommentFormState(
                                            content = state.replyFormContent,
                                            isAnonymous = state.replyFormIsAnonymous,
                                            isSubmitting = state.isSubmittingReply,
                                        ),
                                        onContentChange = {
                                            onAction(PublicationDetailsIntent.UpdateReplyFormContent(it))
                                        },
                                        onAnonymousChange = {
                                            onAction(PublicationDetailsIntent.UpdateReplyFormIsAnonymous(it))
                                        },
                                        onSubmit = { onAction(PublicationDetailsIntent.SubmitReply) },
                                        onCancel = { onAction(PublicationDetailsIntent.CancelReply) },
                                        placeholder = "Write a reply...",
                                    )
                                }
                            }

                            if (state.commentsError != null) {
                                Text(
                                    text = state.commentsError,
                                    color = LocalTalkfrlyColors.current.error,
                                    fontSize = 14.sp,
                                )
                            }

                            CommentList(
                                comments = state.comments,
                                isLoading = state.isLoadingComments,
                                onReply = { comment ->
                                    onAction(PublicationDetailsIntent.StartReply(comment))
                                },
                            )
                        }
                    }
                }
            }
            state.errorMessage != null -> {
                Text(
                    text = state.errorMessage,
                    color = LocalTalkfrlyColors.current.body,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                )
            }
        }
    }
}