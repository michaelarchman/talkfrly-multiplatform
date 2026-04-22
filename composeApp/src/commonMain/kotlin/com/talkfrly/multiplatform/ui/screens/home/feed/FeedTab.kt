package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.talkfrly.multiplatform.ui.components.feeds.FeedCard
import com.talkfrly.multiplatform.ui.screens.splash.SplashScreen
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FeedTab(
    viewModel: FeedTabViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isLoading by viewModel.loadingCount.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onIntent(FeedTabIntent.GetFeed(1, 5))
    }

    if (isLoading > 0) {
        SplashScreen()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        items(state.feed?.publications ?: emptyList()) { item ->
            FeedCard(feedItem = item)
        }
    }
}