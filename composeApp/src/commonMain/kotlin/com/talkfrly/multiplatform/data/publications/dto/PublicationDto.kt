package com.talkfrly.multiplatform.data.publications.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias ModuleTypeDto = String

@Serializable
data class PublicationDto(
    @SerialName("id") val id: String,
    @SerialName("user_id") val userId: String? = null,
    @SerialName("user") val user: UserSummaryDto? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("topic_id") val topicId: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
    @SerialName("module_type") val moduleType: ModuleTypeDto? = null,
    @SerialName("content") val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("video_stream_uid") val videoStreamUid: String? = null,
    @SerialName("video_url") val videoUrl: String? = null,
    @SerialName("video_thumbnail") val videoThumbnail: String? = null,
    @SerialName("video_embed_url") val videoEmbedUrl: String? = null,
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("languages") val languages: List<String> = emptyList(),
    @SerialName("criteria") val criteria: List<CriterionSummaryDto>? = null,
    @SerialName("pseudonym") val pseudonym: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("thread_members_only") val threadMembersOnly: Boolean? = null,
    @SerialName("is_thread_member") val isThreadMember: Boolean,
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("vote_score") val voteScore: Int,
    @SerialName("liked_by_user") val likedByUser: Boolean? = null,
    @SerialName("views") val views: Int? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class UserSummaryDto(
    @SerialName("id") val id: String,
    @SerialName("display_name") val displayName: String? = null,
)

@Serializable
data class CriterionSummaryDto(
    @SerialName("name") val name: String,
    @SerialName("average") val average: Double,
    @SerialName("vote_count") val voteCount: Int,
    @SerialName("user_score") val userScore: Double? = null,
)

@Serializable
data class CriterionInputDto(
    @SerialName("name") val name: String,
    @SerialName("score") val score: Double? = null,
)

@Serializable
data class CreatePublicationRequestDto(
    @SerialName("content") val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean? = null,
    @SerialName("is_private") val isPrivate: Boolean? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("topic_id") val topicId: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("module_type") val moduleType: ModuleTypeDto? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
    @SerialName("image_urls") val imageUrls: List<String>? = null,
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("tags") val tags: List<String>? = null,
    @SerialName("criteria") val criteria: List<CriterionInputDto>? = null,
    @SerialName("thread_members_only") val threadMembersOnly: Boolean? = null,
)

@Serializable
data class PublicationFilterDto(
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("topic_id") val topicId: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("module_type") val moduleType: ModuleTypeDto? = null,
)

@Serializable
data class PublicationListResponseDto(
    @SerialName("publications") val publications: List<PublicationDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class UpdatePublicationRequestDto(
    @SerialName("content") val content: String,
    @SerialName("tags") val tags: List<String>? = null,
    @SerialName("image_urls") val imageUrls: List<String>? = null,
)

@Serializable
data class CreateRatingRequestDto(
    @SerialName("ratings") val ratings: List<CriterionRatingInputDto>,
)

@Serializable
data class CriterionRatingInputDto(
    @SerialName("name") val name: String,
    @SerialName("score") val score: Double,
)
