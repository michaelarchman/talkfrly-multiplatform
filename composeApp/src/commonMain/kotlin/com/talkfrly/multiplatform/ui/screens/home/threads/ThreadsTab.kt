package com.talkfrly.multiplatform.ui.screens.home.threads

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.ui.screens.thread.ThreadFilter
import com.talkfrly.multiplatform.ui.screens.thread.ThreadsIntent
import com.talkfrly.multiplatform.ui.screens.thread.ThreadsViewModel
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.remove_circle

@Composable
fun ThreadsTab(
    viewModel: ThreadsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            viewModel.onIntent(ThreadsIntent.GetThreads)
            viewModel.onIntent(ThreadsIntent.GetJoinedThreads)
            viewModel.onIntent(ThreadsIntent.GetOwnedThreads)
        }
    }

    val ownedThreadIds = state.ownedThreads.map { it.id }.toSet()
    val joinedThreadIds = state.joinedThreads.map { it.id }.toSet()
    val userThreadIds = ownedThreadIds + joinedThreadIds
    val availableThreads = state.allThreads.filterNot { it.id in userThreadIds }
    val selectedThreads = when (state.selectedThreadFilter) {
        ThreadFilter.Popular -> availableThreads
        ThreadFilter.Owned -> state.ownedThreads
        ThreadFilter.Joined -> state.joinedThreads
    }
    val emptyMessage = when (state.selectedThreadFilter) {
        ThreadFilter.Popular -> "No popular threads found"
        ThreadFilter.Owned -> "No owned threads found"
        ThreadFilter.Joined -> "No joined threads found"
    }
    val currentPage = when (state.selectedThreadFilter) {
        ThreadFilter.Popular -> state.allThreadsPage
        ThreadFilter.Owned -> state.ownedThreadsPage
        ThreadFilter.Joined -> state.joinedThreadsPage
    }
    val currentLimit = when (state.selectedThreadFilter) {
        ThreadFilter.Popular -> state.allThreadsLimit
        ThreadFilter.Owned -> state.ownedThreadsLimit
        ThreadFilter.Joined -> state.joinedThreadsLimit
    }
    val currentTotalCount = when (state.selectedThreadFilter) {
        ThreadFilter.Popular -> state.allThreadsTotalCount
        ThreadFilter.Owned -> state.ownedThreadsTotalCount
        ThreadFilter.Joined -> state.joinedThreadsTotalCount
    }
    val hasPreviousPage = currentPage > 1
    val hasNextPage = currentLimit > 0 && currentPage * currentLimit < currentTotalCount

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (state.isLoading) {
            item { Text("Loading threads...") }
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ThreadFilter.entries.forEach { filter ->
                    FilterChip(
                        selected = state.selectedThreadFilter == filter,
                        onClick = {
                            viewModel.onIntent(ThreadsIntent.SelectThreadFilter(filter))
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = LocalTalkfrlyColors.current.backgroundDarker,
                            labelColor = LocalTalkfrlyColors.current.body,
                            selectedContainerColor = LocalTalkfrlyColors.current.primary20,
                            selectedLabelColor = LocalTalkfrlyColors.current.body,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = state.selectedThreadFilter == filter,
                            borderColor = LocalTalkfrlyColors.current.primary60,
                            selectedBorderColor = LocalTalkfrlyColors.current.primary60,
                        ),
                        label = {
                            Text(filter.label)
                        },
                    )
                }
            }
        }

        if (!state.isLoading && selectedThreads.isEmpty() && state.errorMessage == null) {
            item {
                Text(emptyMessage)
            }
        }

        items(selectedThreads, key = { "${state.selectedThreadFilter.name}-${it.id}" }) { thread ->
            ThreadTabCard(
                thread = thread,
                showOwnerChip = state.selectedThreadFilter == ThreadFilter.Owned,
                onJoinClick = if (state.selectedThreadFilter == ThreadFilter.Popular) {
                    { viewModel.onIntent(ThreadsIntent.JoinThread(thread.id)) }
                } else {
                    null
                },
                onLeaveClick = if (state.selectedThreadFilter == ThreadFilter.Joined) {
                    { viewModel.onIntent(ThreadsIntent.LeaveThread(thread.id)) }
                } else {
                    null
                },
            )
        }

        if (hasPreviousPage || hasNextPage) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (hasPreviousPage) {
                        TextButton(
                            onClick = {
                                viewModel.onIntent(
                                    ThreadsIntent.ChangeThreadPage(
                                        filter = state.selectedThreadFilter,
                                        page = currentPage - 1,
                                    ),
                                )
                            },
                        ) {
                            Text(
                                text = "< Back",
                                color = LocalTalkfrlyColors.current.primary60,
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    if (hasNextPage) {
                        TextButton(
                            onClick = {
                                viewModel.onIntent(
                                    ThreadsIntent.ChangeThreadPage(
                                        filter = state.selectedThreadFilter,
                                        page = currentPage + 1,
                                    ),
                                )
                            },
                        ) {
                            Text(
                                text = "Next >",
                                color = LocalTalkfrlyColors.current.primary60,
                            )
                        }
                    }
                }
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
