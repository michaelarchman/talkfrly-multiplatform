package com.talkfrly.multiplatform.domain.feed

data class Feed(
    val feed: List<FeedItem>,
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
    val user : FeedUser,
    val content: String,
    val isAnonymous: Boolean,
    val isPrivate: Boolean,
    val threadMembersOnly: Boolean,
    val threadId: String? = null,
    val imageUrls: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val commentCount: Int,
    val voteScore: Int,
    val likedByUser: Boolean,
    val views: Int,
    val createdAt: String,
    val updatedAt: String,
//    val channelId: String? = null,
//    val threadSlug: String? = null,
//    val threadName: String? = null,
//    val publicationType: String? = null,
//    val articleCategory: String? = null,
//    val isThreadMember: Boolean,
//    val languages: List<String> = emptyList(),
//    val videoId: String? = null,
//    val videoUrl: String? = null,
//    val videoThumbnail: String? = null,
//    val videoEmbedUrl: String? = null,
//    val videoStreamUid: String? = null,
)