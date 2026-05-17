package com.talkfrly.multiplatform.data.chat.repository

import com.talkfrly.multiplatform.data.chat.api.ChatApi
import com.talkfrly.multiplatform.data.chat.mapper.toDomain
import com.talkfrly.multiplatform.data.chat.mapper.toDto
import com.talkfrly.multiplatform.domain.chat.Chat
import com.talkfrly.multiplatform.domain.chat.ChatMessageList
import com.talkfrly.multiplatform.domain.chat.CreateChatRequest
import com.talkfrly.multiplatform.domain.chat.UpdateChatRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map

interface ChatRepository {
    suspend fun createChat(streamId: String, request: CreateChatRequest): DataResult<Chat, DataError.Remote>
    suspend fun getChat(streamId: String): DataResult<Chat, DataError.Remote>
    suspend fun updateChat(streamId: String, request: UpdateChatRequest): DataResult<Chat, DataError.Remote>
    suspend fun deleteChat(streamId: String): DataResult<Unit, DataError.Remote>
    suspend fun getChatMessages(streamId: String, cursor: String?, limit: Int): DataResult<ChatMessageList, DataError.Remote>
}

class ChatRepositoryImpl(
    private val api: ChatApi,
) : ChatRepository {
    override suspend fun createChat(
        streamId: String,
        request: CreateChatRequest,
    ): DataResult<Chat, DataError.Remote> {
        return api.createChat(streamId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun getChat(streamId: String): DataResult<Chat, DataError.Remote> {
        return api.getChat(streamId).map { it.toDomain() }
    }

    override suspend fun updateChat(
        streamId: String,
        request: UpdateChatRequest,
    ): DataResult<Chat, DataError.Remote> {
        return api.updateChat(streamId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun deleteChat(streamId: String): DataResult<Unit, DataError.Remote> {
        return api.deleteChat(streamId)
    }

    override suspend fun getChatMessages(
        streamId: String,
        cursor: String?,
        limit: Int,
    ): DataResult<ChatMessageList, DataError.Remote> {
        return api.getChatMessages(streamId, cursor, limit).map { it.toDomain() }
    }
}