package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.components.feeds.FeedCard
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import com.talkfrly.multiplatform.ui.theme.LocalTalkfrlyColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedTab(
    viewModel: FeedTabViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.loadingCount.collectAsState()
    val listState = rememberLazyListState()
//    val nearBottom by remember {
//        derivedStateOf {
//            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
//            val total = listState.layoutInfo.totalItemsCount
//            total > 0 && lastVisible >= total - 2
//        }
//    }

    LaunchedEffect(Unit) {
        viewModel.onIntent(FeedTabIntent.GetFeed(1, 3))
    }

    if (isLoading > 0) {
        SplashScreen()
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
    }
}