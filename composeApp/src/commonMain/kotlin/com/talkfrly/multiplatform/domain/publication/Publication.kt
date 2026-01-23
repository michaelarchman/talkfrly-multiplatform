package com.talkfrly.multiplatform.domain.publication

data class Publication(
    val id: String,
    val userId: String? = null,
    val user: UserSummary? = null,
    val channelId: String? = null,
    val topicId: String? = null,
    val threadId: String? = null,
    val threadSlug: String? = null,
    val threadName: String? = null,
    val moduleType: ModuleType? = null,
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
    val criteria: List<CriterionSummary>? = null,
    val pseudonym: String? = null,
    val avatarUrl: String? = null,
    val threadMembersOnly: Boolean? = null,
    val isThreadMember: Boolean,
    val commentCount: Int,
    val voteScore: Int,
    val likedByUser: Boolean? = null,
    val views: Int? = null,
    val createdAt: String,
    val updatedAt: String,
)

data class UserSummary(
    val id: String,
    val displayName: String? = null,
)

data class CriterionSummary(
    val name: String,
    val average: Double,
    val voteCount: Int,
    val userScore: Double? = null,
)

enum class ModuleType {
    CHANNEL,
    TOPIC,
    THREAD,
    UNKNOWN,
}

data class PublicationFilter(
    val channelId: String? = null,
    val topicId: String? = null,
    val threadId: String? = null,
    val moduleType: ModuleType? = null,
)

data class PublicationListResponse(
    val publications: List<Publication>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)
