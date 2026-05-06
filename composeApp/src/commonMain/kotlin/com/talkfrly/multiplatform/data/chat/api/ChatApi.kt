package com.talkfrly.multiplatform.data.chat.api

import com.talkfrly.multiplatform.data.chat.dto.ChatMessageListResponseDto
import com.talkfrly.multiplatform.data.chat.dto.ChatResponseDto
import com.talkfrly.multiplatform.data.chat.dto.CreateChatRequestDto
import com.talkfrly.multiplatform.data.chat.dto.UpdateChatRequestDto
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface ChatApi {
    suspend fun createChat(streamId: String, request: CreateChatRequestDto): DataResult<ChatResponseDto, DataError.Remote>
    suspend fun getChat(streamId: String): DataResult<ChatResponseDto, DataError.Remote>
    suspend fun updateChat(streamId: String, request: UpdateChatRequestDto): DataResult<ChatResponseDto, DataError.Remote>
    suspend fun deleteChat(streamId: String): DataResult<Unit, DataError.Remote>
    suspend fun getChatMessages(streamId: String, cursor: String?, limit: Int): DataResult<ChatMessageListResponseDto, DataError.Remote>
}

class ChatApiImpl(
    private val httpClient: HttpClient,
) : ChatApi {
    override suspend fun createChat(
        streamId: String,
        request: CreateChatRequestDto,
    ): DataResult<ChatResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/streams/$streamId/chat",
            body = request,
        )
    }

    override suspend fun getChat(streamId: String): DataResult<ChatResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/streams/$streamId/chat",
        )
    }

    override suspend fun updateChat(
        streamId: String,
        request: UpdateChatRequestDto,
    ): DataResult<ChatResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Patch,
            httpClient = httpClient,
            urlString = "/streams/$streamId/chat",
            body = request,
        )
    }

    override suspend fun deleteChat(streamId: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Delete,
            httpClient = httpClient,
            urlString = "/streams/$streamId/chat",
        )
    }

    override suspend fun getChatMessages(
        streamId: String,
        cursor: String?,
        limit: Int,
    ): DataResult<ChatMessageListResponseDto, DataError.Remote> {
        val params = mutableMapOf<String, Any>("limit" to limit)
        cursor?.let { params["cursor"] = it }
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/streams/$streamId/chat/messages",
            queryParams = params,
        )
    }
}