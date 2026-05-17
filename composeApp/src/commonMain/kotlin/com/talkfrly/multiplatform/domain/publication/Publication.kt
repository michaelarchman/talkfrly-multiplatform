package com.talkfrly.multiplatform.domain.publication

import com.talkfrly.multiplatform.domain.ranking.Ranking

data class Publication(
    val id: String,
    val user: UserSummary? = null,
    val channelId: String? = null,
    val threadId: String? = null,
    val threadSlug: String? = null,
    val threadName: String? = null,
    val type: String? = null,
    val ranking: Ranking? = null,
    val articleCategory: String? = null,
    val content: String,
    val isAnonymous: Boolean,
    val isPrivate: Boolean,
    val imageUrls: List<String> = emptyList(),
    val videoId: String? = null,
    val videoStreamUid: String? = null,
    val videoUrl: String? = null,
    val videoThumbnail: String? = null,
    val videoEmbedUrl: String? = null,
    val tags: List<String> = emptyList(),
    val languages: List<String> = emptyList(),
    val avatarUrl: String? = null,
    val threadMembersOnly: Boolean = false,
    val courseMembersOnly: Boolean = false,
    val courseId: String? = null,
    val lessonId: String? = null,
    val lessonTitle: String? = null,
    val moduleTitle: String? = null,
    val isThreadMember: Boolean = false,
    val commentCount: Int,
    val voteScore: Int,
    val likedByUser: Boolean,
    val likeCount: Int,
    val views: Int,
    val createdAt: String,
    val updatedAt: String,
)

data class UserSummary(
    val id: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
)

data class PublicationFilter(
    val channelId: String? = null,
    val threadId: String? = null,
    val type: String? = null,
    val sort: String? = null,
    val private: Boolean? = null,
)

data class PublicationList(
    val publications: List<Publication>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)