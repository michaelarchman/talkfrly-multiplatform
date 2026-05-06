package com.talkfrly.multiplatform.ui.screens.thread

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.chevron_left
import talkfrly_multiplatform.composeapp.generated.resources.remove_circle

@Composable
fun ThreadScreenRoot(
    viewModel: ThreadViewModel = koinViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(ThreadIntent.GetThreads)
        viewModel.onIntent(ThreadIntent.GetJoinedThreads)
        viewModel.onIntent(ThreadIntent.GetOwnedThreads)
    }

    ThreadScreen(
        state = state,
        onBackClick = { navController.popBackStack() },
        onAction = viewModel::onIntent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThreadScreen(
    state: ThreadState,
    onBackClick: () -> Unit,
    onAction: (ThreadIntent) -> Unit,
) {
    val ownedThreadIds = state.ownedThreads.map { it.id }.toSet()
    val joinedThreadIds = state.joinedThreads.map { it.id }.toSet()
    val userThreadIds = ownedThreadIds + joinedThreadIds
    val availableThreads = state.allThreads.filterNot { it.id in userThreadIds }

    Scaffold(
        containerColor = LocalTalkfrlyColors.current.background,
        contentColor = LocalTalkfrlyColors.current.body,
        topBar = {
            TopAppBar(
                title = { Text("Threads") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = LocalTalkfrlyColors.current.background,
                    titleContentColor = LocalTalkfrlyColors.current.body,
                    navigationIconContentColor = LocalTalkfrlyColors.current.body,
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {

            if (state.isLoading) {
                item {
                    Text("Loading threads...")
                }
            }

            state.errorMessage?.let { message ->
                item {
                    Text(
                        text = message,
                        color = Color.Red,
                    )
                }
            }

            item {
                Text(
                    text = "Threads",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            if (!state.isLoading && availableThreads.isEmpty() && state.errorMessage == null) {
                item {
                    Text("No threads found")
                }
            }

            items(availableThreads, key = { it.id }) { thread ->
                ThreadCard(
                    thread = thread,
                    onJoinClick = { onAction(ThreadIntent.JoinThread(thread.id)) },
                )
            }

            if (state.ownedThreads.isNotEmpty()) {
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = LocalTalkfrlyColors.current.primary20,
                    )
                }

                item {
                    Text(
                        text = "Owned threads",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                items(state.ownedThreads, key = { "owned-${it.id}" }) { thread ->
                    ThreadCard(
                        thread = thread,
                        showOwnerChip = true,
                    )
                }
            }

            if (state.joinedThreads.isNotEmpty()) {
                item {
                    HorizontalDivider(
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = LocalTalkfrlyColors.current.primary20,
                    )
                }

                item {
                    Text(
                        text = "Joined threads",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                items(state.joinedThreads, key = { "my-${it.id}" }) { thread ->
                    ThreadCard(
                        thread = thread,
                        onLeaveClick = { onAction(ThreadIntent.LeaveThread(thread.id)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun ThreadCard(
    thread: Thread,
    showOwnerChip: Boolean = false,
    onJoinClick: (() -> Unit)? = null,
    onLeaveClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = LocalTalkfrlyColors.current.primary60,
                shape = RoundedCornerShape(12.dp),
            )
            .background(
                color = LocalTalkfrlyColors.current.backgroundDarker,
                shape = RoundedCornerShape(12.dp),
            )
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = thread.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                Text("${thread.memberCount} ${if (thread.memberCount > 1) " members" else " member"}")

                if (showOwnerChip) {
                    OwnerChip()
                }
            }

            thread.description?.let {
                Text(
                    text = thread.description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(.8f),
                )
            }
        }

        onJoinClick?.let {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = it,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = vectorResource(Res.drawable.add_circle),
                    contentDescription = "follow",
                )
            }
        }

        onLeaveClick?.let {
            IconButton(
                modifier = Modifier.size(56.dp),
                onClick = it,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = vectorResource(Res.drawable.remove_circle),
                    contentDescription = "leave",
                )
            }
        }
    }
}

@Composable
private fun OwnerChip() {
    Text(
        text = "Owner",
        color = LocalTalkfrlyColors.current.primary60,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .border(
                width = 1.dp,
                color = LocalTalkfrlyColors.current.primary60,
                shape = RoundedCornerShape(999.dp),
            )
            .padding(horizontal = 8.dp, vertical = 3.dp),
    )
}
