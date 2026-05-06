package com.talkfrly.multiplatform.domain.feed

import com.talkfrly.multiplatform.domain.ranking.Ranking

data class Feed(
    val publications: List<FeedItem>,
    val page: Int,
    val limit: Int,
)

data class FeedUser(
    val id: String,
    val displayName: String,
    val avatarUrl: String? = null,
)

data class FeedItem(
    val id: String,
    val user: FeedUser? = null,
    val content: String,
    val publicationType: String? = null,
    val ranking: Ranking? = null,
    val articleCategory: String? = null,
    val isAnonymous: Boolean,
    val isPrivate: Boolean,
    val threadMembersOnly: Boolean,
    val threadId: String? = null,
    val threadSlug: String? = null,
    val threadName: String? = null,
    val imageUrls: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val videoId: String? = null,
    val videoStreamUid: String? = null,
    val videoUrl: String? = null,
    val videoThumbnail: String? = null,
    val videoEmbedUrl: String? = null,
    val commentCount: Int,
    val voteScore: Int,
    val likedByUser: Boolean,
    val likeCount: Int,
    val views: Int,
    val createdAt: String,
    val updatedAt: String,
)