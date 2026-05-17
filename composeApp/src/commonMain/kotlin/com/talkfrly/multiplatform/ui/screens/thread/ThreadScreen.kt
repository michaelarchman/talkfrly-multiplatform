package com.talkfrly.multiplatform.ui.screens.thread

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButton
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButtonType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.ui.components.feed.FeedCard
import com.talkfrly.multiplatform.ui.nav.PublicationRoute
import com.talkfrly.multiplatform.ui.screens.home.feed.FeedTabIntent
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.remove_circle
import talkfrly_multiplatform.composeapp.generated.resources.whatshot

@Composable
fun ThreadScreenRoot(
    threadId: String,
    viewModel: ThreadViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(threadId) {
        viewModel.onIntent(ThreadIntent.GetThreadById(threadId))
        viewModel.onIntent(ThreadIntent.GetJoinedThreads)
        viewModel.onIntent(ThreadIntent.GetOwnedThreads)
        viewModel.onIntent(ThreadIntent.GetPublicationsForThread(threadId))
    }

    ThreadScreen(
        state = state,
        threadId = threadId,
        onBackClick = { navController.popBackStack() },
        onAction = viewModel::onIntent,
        onPublicationClick = { navController.navigate(PublicationRoute(it)) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThreadScreen(
    state: ThreadState,
    threadId: String,
    onBackClick: () -> Unit,
    onAction: (ThreadIntent) -> Unit,
    onPublicationClick: (String) -> Unit,
) {
    val colors = LocalTalkfrlyColors.current
    val isOwned = state.ownedThreads.any { it.id == threadId }
    val isJoined = state.joinedThreads.any { it.id == threadId }

    Scaffold(
        containerColor = colors.background,
        contentColor = colors.body,
        topBar = {
            TopAppBar(
                title = { Text(state.currentThread?.name ?: "") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colors.background,
                    titleContentColor = colors.body,
                    navigationIconContentColor = colors.body,
                ),
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.chevron_left),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        when {
            state.isLoading && state.currentThread == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = colors.primary)
                }
            }
            state.errorMessage != null && state.currentThread == null -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(state.errorMessage, color = Color.Red)
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    state.currentThread?.let { thread ->
                        item {
                            ThreadHeader(
                                thread = thread,
                                isOwned = isOwned,
                                isJoined = isJoined,
                                onJoin = { onAction(ThreadIntent.JoinThread(thread.id)) },
                                onLeave = { onAction(ThreadIntent.LeaveThread(thread.id)) },
                            )
                        }
                        item {
                            HorizontalDivider(color = colors.primary20)
                        }
                    }

                    if (state.publications.isEmpty() && !state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("No posts yet", color = colors.bodyMuted, fontSize = 14.sp)
                            }
                        }
                    }

                    items(state.publications, key = { it.id }) { feedItem ->
                        FeedCard(
                            feedItem = feedItem,
                            onAction = {},
                            onItemClick = { onPublicationClick(it.id) },
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ThreadHeader(
    thread: Thread,
    isOwned: Boolean,
    isJoined: Boolean,
    onJoin: () -> Unit,
    onLeave: () -> Unit,
) {
    val colors = LocalTalkfrlyColors.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "${thread.memberCount} ${if (thread.memberCount == 1) "member" else "members"}",
                color = colors.bodyMuted,
                fontSize = 13.sp,
            )
            if (isOwned) {
                Text(
                    text = "Owner",
                    color = colors.primary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .border(1.dp, colors.primary, RoundedCornerShape(999.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }
        }

        thread.description?.let {
            Text(text = it, color = colors.bodyMuted, fontSize = 14.sp)
        }

        if (!isOwned) {
            if (isJoined) {
                InteractionStatButton(
                    isActive = true,
                    type = InteractionStatButtonType.OUTLINED,
                    icon = Res.drawable.remove_circle,
                    label = "Leave",
                    onClick = onLeave,
                )
            } else {
                InteractionStatButton(
                    isActive = false,
                    type = InteractionStatButtonType.OUTLINED,
                    icon = Res.drawable.add_circle,
                    label = "Join",
                    onClick = onJoin,
                )
            }
        }
    }
}