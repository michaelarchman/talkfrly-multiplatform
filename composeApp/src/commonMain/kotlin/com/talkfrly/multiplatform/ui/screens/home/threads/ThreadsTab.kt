package com.talkfrly.multiplatform.ui.screens.home.threads

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButton
import com.talkfrly.multiplatform.ui.components.buttons.InteractionStatButtonType
import com.talkfrly.multiplatform.ui.screens.thread.ThreadFilter
import com.talkfrly.multiplatform.ui.screens.thread.ThreadIntent
import com.talkfrly.multiplatform.ui.screens.thread.ThreadViewModel
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel
import talkfrly_multiplatform.composeapp.generated.resources.Res
import talkfrly_multiplatform.composeapp.generated.resources.add_circle
import talkfrly_multiplatform.composeapp.generated.resources.loupe_plus
import talkfrly_multiplatform.composeapp.generated.resources.whatshot

@Composable
fun ThreadsTab(
    onThreadClick: (String) -> Unit,
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

    val displayList = when (state.filter) {
        ThreadFilter.ALL -> state.allThreads.filterNot { it.id in userThreadIds }
        ThreadFilter.MINE -> state.ownedThreads
        ThreadFilter.MEMBER -> state.joinedThreads
    }

    val colors = LocalTalkfrlyColors.current

    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.backgroundLighter)
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp, bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TextField(
                value = state.searchQuery,
                onValueChange = { q ->
                    viewModel.onIntent(ThreadIntent.SetSearchQuery(q))
                    if (q.length >= 3) viewModel.onIntent(ThreadIntent.SearchThreads(q))
                    else if (q.isEmpty()) viewModel.onIntent(ThreadIntent.SearchThreads(""))
                },
                placeholder = {
                    Text(
                        text = "Search threads...",
                        color = colors.bodyMuted,
                        fontSize = 14.sp,
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = vectorResource(Res.drawable.loupe_plus),
                        contentDescription = null,
                        tint = colors.bodyMuted,
                        modifier = Modifier.size(20.dp),
                    )
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colors.backgroundDarker,
                    unfocusedContainerColor = colors.backgroundDarker,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedTextColor = colors.body,
                    unfocusedTextColor = colors.body,
                    focusedPlaceholderColor = colors.bodyMuted,
                    unfocusedPlaceholderColor = colors.bodyMuted,
                    focusedLeadingIconColor = colors.bodyMuted,
                    unfocusedLeadingIconColor = colors.bodyMuted,
                ),
            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                val filters = listOf(
                    ThreadFilter.ALL to "Explore",
                    ThreadFilter.MINE to "Mine",
                    ThreadFilter.MEMBER to "Member",
                )
                items(filters) { (filter, label) ->
                    FilterChip(
                        selected = state.filter == filter,
                        onClick = { viewModel.onIntent(ThreadIntent.SetFilter(filter)) },
                        label = { Text(label, fontSize = 13.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colors.primary,
                            selectedLabelColor = colors.background,
                            containerColor = colors.backgroundDarker,
                            labelColor = colors.bodyMuted,
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = state.filter == filter,
                            borderColor = colors.primary20,
                            selectedBorderColor = Color.Transparent,
                        ),
                    )
                }
            }
        }

        val isSearching = state.searchQuery.length >= 3
        val activeList = if (isSearching) state.searchResults else displayList

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            item {}

            if (activeList.isEmpty() && !state.isLoading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(top = 48.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = if (isSearching) "No threads found"
                            else when (state.filter) {
                                ThreadFilter.MINE -> "You don't own any threads yet"
                                ThreadFilter.MEMBER -> "You haven't joined any threads yet"
                                ThreadFilter.ALL -> "No threads to explore"
                            },
                            color = colors.bodyMuted,
                            fontSize = 14.sp,
                        )
                    }
                }
            }

            items(activeList, key = { if (isSearching) "search-${it.id}" else "${state.filter}-${it.id}" }) { thread ->
                val isOwned = thread.id in ownedThreadIds
                val isJoined = thread.id in joinedThreadIds

                ThreadTabCard(
                    thread = thread,
                    isOwned = isOwned,
                    isJoined = isJoined,
                    onClick = { onThreadClick(thread.id) },
                    onJoinClick = if (!isOwned && !isJoined) {
                        { viewModel.onIntent(ThreadIntent.JoinThread(thread.id)) }
                    } else null,
                )
            }

            item {}
        }
    }
}

@Composable
private fun ThreadTabCard(
    thread: Thread,
    isOwned: Boolean = false,
    isJoined: Boolean = false,
    onClick: () -> Unit,
    onJoinClick: (() -> Unit)? = null,
) {
    val colors = LocalTalkfrlyColors.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, colors.primary20, RoundedCornerShape(12.dp))
            .background(colors.backgroundDarker, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = thread.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colors.body,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false),
                )

                if (thread.isLive) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .background(Color.Red.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp),
                    ) {
                        Icon(
                            imageVector = vectorResource(Res.drawable.whatshot),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(12.dp),
                        )
                        Text(text = "LIVE", color = Color.Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = "${thread.memberCount} ${if (thread.memberCount == 1) "member" else "members"}",
                    color = colors.bodyMuted,
                    fontSize = 12.sp,
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
                } else if (isJoined) {
                    Text(
                        text = "Member",
                        color = colors.accent,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .border(1.dp, colors.accent, RoundedCornerShape(999.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp),
                    )
                }
            }

            thread.description?.let {
                Text(
                    text = it,
                    color = colors.bodyMuted,
                    fontSize = 13.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        onJoinClick?.let {
            InteractionStatButton(
                isActive = false,
                type = InteractionStatButtonType.OUTLINED,
                icon = Res.drawable.add_circle,
                label = "Join",
                onClick = it,
            )
        }
    }
}