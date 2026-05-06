package com.talkfrly.multiplatform.domain.chat

data class Chat(
    val id: String,
    val streamId: String,
    val enabled: Boolean,
    val persistMessages: Boolean,
    val status: String,
    val createdAt: String,
    val closedAt: String? = null,
)

data class ChatMessage(
    val id: String,
    val chatId: String,
    val streamId: String,
    val userId: String,
    val username: String,
    val avatarUrl: String? = null,
    val clientMessageId: String? = null,
    val content: String,
    val kind: String,
    val createdAt: String,
)

data class ChatMessageList(
    val items: List<ChatMessage>,
    val cursor: String? = null,
)

data class CreateChatRequest(
    val persistMessages: Boolean = false,
)

data class UpdateChatRequest(
    val enabled: Boolean? = null,
    val persistMessages: Boolean? = null,
)