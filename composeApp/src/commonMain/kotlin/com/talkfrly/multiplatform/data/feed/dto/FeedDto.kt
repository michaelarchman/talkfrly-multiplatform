package com.talkfrly.multiplatform.data.feed.dto

import com.talkfrly.multiplatform.data.ranking.dto.RankingResponseDto
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
    @SerialName("user") val user: FeedUserDto? = null,
    @SerialName("content") val content: String,
    @SerialName("publication_type") val publicationType: String? = null,
    @SerialName("ranking") val ranking: RankingResponseDto? = null,
    @SerialName("article_category") val articleCategory: String? = null,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("is_private") val isPrivate: Boolean,
    @SerialName("thread_members_only") val threadMembersOnly: Boolean,
    @SerialName("course_members_only") val courseMembersOnly: Boolean = false,
    @SerialName("course_id") val courseId: String? = null,
    @SerialName("lesson_id") val lessonId: String? = null,
    @SerialName("lesson_title") val lessonTitle: String? = null,
    @SerialName("module_title") val moduleTitle: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("tags") val tags: List<String> = emptyList(),
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("video_stream_uid") val videoStreamUid: String? = null,
    @SerialName("video_url") val videoUrl: String? = null,
    @SerialName("video_thumbnail") val videoThumbnail: String? = null,
    @SerialName("video_embed_url") val videoEmbedUrl: String? = null,
    @SerialName("comment_count") val commentCount: Int,
    @SerialName("vote_score") val voteScore: Int,
    @SerialName("liked_by_user") val likedByUser: Boolean,
    @SerialName("like_count") val likeCount: Int,
    @SerialName("views") val views: Int,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)