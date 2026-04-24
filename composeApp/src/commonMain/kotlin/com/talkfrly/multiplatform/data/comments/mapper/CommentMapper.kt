package com.talkfrly.multiplatform.data.comments.mapper

import com.talkfrly.multiplatform.data.comments.dto.CommentDto
import com.talkfrly.multiplatform.data.comments.dto.CommentListResponseDto
import com.talkfrly.multiplatform.data.comments.dto.CreateCommentRequestDto
import com.talkfrly.multiplatform.data.publications.mapper.toDomain
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.comment.CommentList
import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest
import kotlin.time.Instant

fun CommentDto.toDomain(): Comment = Comment(
    id = id,
    publicationId = publicationId,
    userId = userId,
    user = user?.toDomain(),
    parentCommentId = parentCommentId,
    content = content,
    isAnonymous = isAnonymous,
    imageUrls = imageUrls,
    videoId = videoId,
    videoEmbedUrl = videoEmbedUrl,
    createdAt = Instant.parse(createdAt),
    updatedAt = Instant.parse(updatedAt),
    replies = replies.map { it.toDomain() },
)

fun CommentListResponseDto.toDomain(): CommentList = CommentList(
    comments = comments.map { it.toDomain() },
    totalCount = totalCount,
)

fun CreateCommentRequest.toDto(): CreateCommentRequestDto = CreateCommentRequestDto(
    content = content,
    isAnonymous = isAnonymous,
    parentCommentId = parentCommentId,
    imageUrls = imageUrls,
)