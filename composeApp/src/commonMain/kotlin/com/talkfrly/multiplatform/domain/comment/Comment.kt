package com.talkfrly.multiplatform.domain.comment

import com.talkfrly.multiplatform.domain.publication.UserSummary
import kotlin.time.Instant

data class Comment(
    val id: String,
    val publicationId: String,
    val userId: String? = null,
    val user: UserSummary? = null,
    val parentCommentId: String? = null,
    val content: String,
    val isAnonymous: Boolean,
    val imageUrls: List<String> = emptyList(),
    val videoId: String? = null,
    val videoEmbedUrl: String? = null,
    val createdAt: Instant,
    val updatedAt: Instant,
    val replies: List<Comment> = emptyList(),
)

data class CommentList(
    val comments: List<Comment>,
    val totalCount: Int,
)

data class CreateCommentRequest(
    val publicationId: String,
    val content: String,
    val isAnonymous: Boolean = false,
    val parentCommentId: String? = null,
    val imageUrls: List<String> = emptyList(),
)