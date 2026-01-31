package com.talkfrly.multiplatform.data.comments.api

import com.talkfrly.multiplatform.data.comments.dto.CommentListResponseDto
import com.talkfrly.multiplatform.data.comments.dto.CommentDto
import com.talkfrly.multiplatform.data.comments.dto.CreateCommentRequestDto
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface CommentApi {
    suspend fun getComments(publicationId: String): DataResult<CommentListResponseDto, DataError.Remote>
    suspend fun createComment(publicationId: String, request: CreateCommentRequestDto): DataResult<CommentDto, DataError.Remote>
}

class CommentApiImpl(
    private val httpClient: HttpClient,
) : CommentApi {
    override suspend fun getComments(publicationId: String): DataResult<CommentListResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/comments",
        )
    }

    override suspend fun createComment(
        publicationId: String,
        request: CreateCommentRequestDto,
    ): DataResult<CommentDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/comments",
            body = request,
        )
    }
}