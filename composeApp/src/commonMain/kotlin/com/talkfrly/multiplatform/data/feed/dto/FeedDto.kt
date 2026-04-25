package com.talkfrly.multiplatform.data.feed.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedResponseDto(
    @SerialName("feed") val feed: List<FeedItemResponseDto>,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class FeedUserDto(
    @SerialName("id") val id: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
)

@Serializable
data class FeedItemResponseDto(
    @SerialName("id") val id: String,
    @SerialName("user") val user: FeedUserDto,
    @SerialName("content") val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("thread_members_only") val threadMembersOnly: Boolean,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("vote_score") val voteScore: Int,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("liked_by_user") val likedByUser: Boolean,
    @SerialName("views") val views: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)