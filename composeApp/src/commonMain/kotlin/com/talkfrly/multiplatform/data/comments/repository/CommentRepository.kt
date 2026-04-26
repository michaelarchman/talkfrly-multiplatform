package com.talkfrly.multiplatform.data.comments.repository

import com.talkfrly.multiplatform.data.comments.api.CommentApi
import com.talkfrly.multiplatform.data.comments.mapper.toDomain
import com.talkfrly.multiplatform.data.comments.mapper.toDto
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.comment.CommentList
import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map

interface CommentRepository {
    suspend fun getComments(publicationId: String): DataResult<CommentList, DataError.Remote>
    suspend fun createComment(request: CreateCommentRequest): DataResult<Comment, DataError.Remote>
}

class CommentRepositoryImpl(
    private val api: CommentApi,
) : CommentRepository {
    override suspend fun getComments(publicationId: String): DataResult<CommentList, DataError.Remote> {
        return api.getComments(publicationId).map { it.toDomain() }
    }

    override suspend fun createComment(request: CreateCommentRequest): DataResult<Comment, DataError.Remote> {
        return api.postComment(request.publicationId, request.toDto()).map { it.toDomain() }
    }
}