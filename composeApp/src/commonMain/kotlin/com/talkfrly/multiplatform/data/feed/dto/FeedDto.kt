package com.talkfrly.multiplatform.data.feed.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FeedResponseDto(
    @SerialName("feed") val feed: List<FeedItemResponseDto>,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class FeedItemResponseDto(
    @SerialName("id") val id: String,
    @SerialName("domain") val domain: JsonElement? = null,
    @SerialName("channel_id") val channelId: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
    @SerialName("publication_type") val publicationType: String? = null,
    @SerialName("article_category") val articleCategory: String? = null,
    @SerialName("content") val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("thread_members_only") val threadMembersOnly: Boolean,
    @SerialName("is_thread_member") val isThreadMember: Boolean,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("languages") val languages: List<String> = emptyList(),
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("video_url") val videoUrl: String? = null,
    @SerialName("video_thumbnail") val videoThumbnail: String? = null,
    @SerialName("video_embed_url") val videoEmbedUrl: String? = null,
    @SerialName("video_stream_uid") val videoStreamUid: String? = null,
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("vote_score") val voteScore: Int,
    @SerialName("liked_by_user") val likedByUser: Boolean,
    @SerialName("views") val views: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)