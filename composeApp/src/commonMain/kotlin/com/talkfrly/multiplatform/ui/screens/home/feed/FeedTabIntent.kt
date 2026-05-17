package com.talkfrly.multiplatform.ui.screens.home.feed

sealed class FeedTabIntent {
    data class GetFeed(val page: Int, val limit: Int) : FeedTabIntent()
    data class LikePublication(val publicationId: String) : FeedTabIntent()
    data class UnlikePublication(val publicationId: String) : FeedTabIntent()
    data class VoteRankingItem(val publicationId: String, val itemId: String, val value: Int) : FeedTabIntent()
}