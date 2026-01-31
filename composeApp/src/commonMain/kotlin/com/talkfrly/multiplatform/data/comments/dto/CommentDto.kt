package com.talkfrly.multiplatform.data.comments.dto

import com.talkfrly.multiplatform.data.publications.dto.UserSummaryDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CommentDto(
    val id: String,
    @SerialName("post_id") val publicationId: String,
    @SerialName("user_id") val userId: String? = null,
    val user: UserSummaryDto? = null,
    @SerialName("parent_comment_id") val parentCommentId: String? = null,
    val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
    @SerialName("video_id") val videoId: String? = null,
    @SerialName("video_embed_url") val videoEmbedUrl: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
    val replies: List<CommentDto> = emptyList(),
)

@Serializable
data class CommentListResponseDto(
    val comments: List<CommentDto>,
    @SerialName("count") val totalCount: Int,
)

@Serializable
data class CreateCommentRequestDto(
    val content: String,
    @SerialName("is_anonymous") val isAnonymous: Boolean = false,
    @SerialName("parent_comment_id") val parentCommentId: String? = null,
    @SerialName("image_urls") val imageUrls: List<String> = emptyList(),
)