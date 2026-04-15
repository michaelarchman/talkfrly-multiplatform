package com.talkfrly.multiplatform.domain.feed

import kotlinx.serialization.json.JsonElement

data class Feed(
    val feed: List<FeedItem>,
    val page: Int,
    val limit: Int,
)

data class FeedItem(
    val id: String,
    val domain: JsonElement? = null,
    val channelId: String? = null,
    val threadId: String? = null,
    val threadSlug: String? = null,
    val threadName: String? = null,
    val publicationType: String? = null,
    val articleCategory: String? = null,
    val content: String,
    val isAnonymous: Boolean,
    val isPrivate: Boolean,
    val threadMembersOnly: Boolean,
    val isThreadMember: Boolean,
    val imageUrls: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val avatarUrl: String? = null,
    val videoId: String? = null,
    val videoUrl: String? = null,
    val videoThumbnail: String? = null,
    val videoEmbedUrl: String? = null,
    val videoStreamUid: String? = null,
    val commentCount: Int,
    val voteScore: Int,
    val likedByUser: Boolean,
    val views: Int,
    val createdAt: String,
    val updatedAt: String,
)
