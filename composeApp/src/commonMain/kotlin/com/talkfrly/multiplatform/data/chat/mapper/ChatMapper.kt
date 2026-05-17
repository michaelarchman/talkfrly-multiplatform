package com.talkfrly.multiplatform.data.chat.mapper

import com.talkfrly.multiplatform.data.chat.dto.ChatMessageItemDto
import com.talkfrly.multiplatform.data.chat.dto.ChatMessageListResponseDto
import com.talkfrly.multiplatform.data.chat.dto.ChatResponseDto
import com.talkfrly.multiplatform.data.chat.dto.CreateChatRequestDto
import com.talkfrly.multiplatform.data.chat.dto.UpdateChatRequestDto
import com.talkfrly.multiplatform.domain.chat.Chat
import com.talkfrly.multiplatform.domain.chat.ChatMessage
import com.talkfrly.multiplatform.domain.chat.ChatMessageList
import com.talkfrly.multiplatform.domain.chat.CreateChatRequest
import com.talkfrly.multiplatform.domain.chat.UpdateChatRequest

fun ChatResponseDto.toDomain(): Chat = Chat(
    id = id,
    streamId = streamId,
    enabled = enabled,
    persistMessages = persistMessages,
    status = status,
    createdAt = createdAt,
    closedAt = closedAt,
)

fun ChatMessageItemDto.toDomain(): ChatMessage = ChatMessage(
    id = id,
    chatId = chatId,
    streamId = streamId,
    userId = userId,
    username = username,
    avatarUrl = avatarUrl,
    clientMessageId = clientMessageId,
    content = content,
    kind = kind,
    createdAt = createdAt,
)

fun ChatMessageListResponseDto.toDomain(): ChatMessageList = ChatMessageList(
    items = items.map { it.toDomain() },
    cursor = cursor,
)

fun CreateChatRequest.toDto(): CreateChatRequestDto = CreateChatRequestDto(
    persistMessages = persistMessages,
)

fun UpdateChatRequest.toDto(): UpdateChatRequestDto = UpdateChatRequestDto(
    enabled = enabled,
    persistMessages = persistMessages,
)