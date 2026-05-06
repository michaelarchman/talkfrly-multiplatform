package com.talkfrly.multiplatform.data.chat.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponseDto(
    @SerialName("id") val id: String,
    @SerialName("stream_id") val streamId: String,
    @SerialName("enabled") val enabled: Boolean,
    @SerialName("persist_messages") val persistMessages: Boolean,
    @SerialName("status") val status: String,
    @SerialName("created_at") val createdAt: String,
    @SerialName("closed_at") val closedAt: String? = null,
)

@Serializable
data class ChatMessageItemDto(
    @SerialName("id") val id: String,
    @SerialName("chat_id") val chatId: String,
    @SerialName("stream_id") val streamId: String,
    @SerialName("user_id") val userId: String,
    @SerialName("username") val username: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("client_message_id") val clientMessageId: String? = null,
    @SerialName("content") val content: String,
    @SerialName("kind") val kind: String,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class ChatMessageListResponseDto(
    @SerialName("items") val items: List<ChatMessageItemDto>,
    @SerialName("cursor") val cursor: String? = null,
)

@Serializable
data class CreateChatRequestDto(
    @SerialName("persist_messages") val persistMessages: Boolean? = null,
)

@Serializable
data class UpdateChatRequestDto(
    @SerialName("enabled") val enabled: Boolean? = null,
    @SerialName("persist_messages") val persistMessages: Boolean? = null,
)