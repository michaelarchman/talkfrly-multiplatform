package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.components.feed.FeedCard
import com.talkfrly.multiplatform.ui.components.feed.FeedCardSkeleton
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedTab(
    viewModel: FeedTabViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.loadingCount.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(FeedTabIntent.GetFeed(1, 3))
    }

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(state.visiblePublications, key = { it.id }) { item ->
            FeedCard(feedItem = item, onAction = {})
            HorizontalDivider(color = LocalTalkfrlyColors.current.backgroundLighter)
        }
        if (isLoading > 0) {
            items(3) {
                FeedCardSkeleton()
                HorizontalDivider(color = LocalTalkfrlyColors.current.backgroundLighter)
            }
        }

        item {
            Button(
                onClick = { viewModel.onIntent(FeedTabIntent.GetFeed(page = state.feed?.page?.plus(1) ?: 1, limit = 3)) }
            ) {
                Text("Get 3 more pubs")
            }
        }
    }
}