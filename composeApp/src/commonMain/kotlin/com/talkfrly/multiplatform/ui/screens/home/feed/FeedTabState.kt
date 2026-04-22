package com.talkfrly.multiplatform.ui.screens.home.feed

import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.feed.Feed
import com.talkfrly.multiplatform.domain.feed.FeedItem

data class FeedTabState(
    val visiblePublications: List<FeedItem> = emptyList(),
    val feed: Feed? = null,
    val isLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasNextPage: Boolean = true,
    val error: DataError.Remote? = null,
)