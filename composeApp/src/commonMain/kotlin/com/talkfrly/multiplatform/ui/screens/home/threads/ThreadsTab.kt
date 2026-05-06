package com.talkfrly.multiplatform.ui.screens.home.threads

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.ui.screens.thread.ThreadIntent
import com.talkfrly.multiplatform.ui.screens.thread.ThreadViewModel
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.remove_circle

@Composable
fun ThreadsTab(
    viewModel: ThreadViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.onIntent(ThreadIntent.GetThreads)
            viewModel.onIntent(ThreadIntent.GetJoinedThreads)
            viewModel.onIntent(ThreadIntent.GetOwnedThreads)
        }
    }

    val ownedThreadIds = state.ownedThreads.map { it.id }.toSet()
    val joinedThreadIds = state.joinedThreads.map { it.id }.toSet()
    val userThreadIds = ownedThreadIds + joinedThreadIds
    val availableThreads = state.allThreads.filterNot { it.id in userThreadIds }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (state.isLoading) {
            item { Text("Loading threads...") }
        }

        items(availableThreads, key = { it.id }) { thread ->
            ThreadTabCard(
                thread = thread,
                onJoinClick = { viewModel.onIntent(ThreadIntent.JoinThread(thread.id)) },
            )
        }

        if (state.ownedThreads.isNotEmpty()) {
            item {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = LocalTalkfrlyColors.current.primary20,
                )
                Text("Owned threads", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            items(state.ownedThreads, key = { "owned-${it.id}" }) { thread ->
                ThreadTabCard(thread = thread, showOwnerChip = true)
            }
        }

        if (state.joinedThreads.isNotEmpty()) {
            item {
                HorizontalDivider(
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = LocalTalkfrlyColors.current.primary20,
                )
                Text("Joined threads", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
            items(state.joinedThreads, key = { "joined-${it.id}" }) { thread ->
                ThreadTabCard(
                    thread = thread,
                    onLeaveClick = { viewModel.onIntent(ThreadIntent.LeaveThread(thread.id)) },
                )
            }
        }

        item { } // bottom spacing
    }
}

@Composable
private fun ThreadTabCard(
    thread: Thread,
    showOwnerChip: Boolean = false,
    onJoinClick: (() -> Unit)? = null,
    onLeaveClick: (() -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.primary60, RoundedCornerShape(12.dp))
            .background(colors.backgroundDarker, RoundedCornerShape(12.dp))
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
                Text(text = thread.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Text("${thread.memberCount} ${if (thread.memberCount > 1) "members" else "member"}")
                if (showOwnerChip) {
                    Text(
                        text = "Owner",
                        color = colors.primary60,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .border(1.dp, colors.primary60, RoundedCornerShape(999.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp),
                    )
                }
            }
            thread.description?.let {
                Text(
                    text = it,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(.8f),
                )
            }
        }

        onJoinClick?.let {
            IconButton(modifier = Modifier.size(56.dp), onClick = it) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = vectorResource(Res.drawable.add_circle),
                    contentDescription = "join",
                )
            }
        }
        onLeaveClick?.let {
            IconButton(modifier = Modifier.size(56.dp), onClick = it) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = vectorResource(Res.drawable.remove_circle),
                    contentDescription = "leave",
                )
            }
        }
    }
}