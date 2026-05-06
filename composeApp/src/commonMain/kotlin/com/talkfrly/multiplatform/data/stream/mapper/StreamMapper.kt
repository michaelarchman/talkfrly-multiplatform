package com.talkfrly.multiplatform.data.stream.mapper

import com.talkfrly.multiplatform.data.stream.dto.StreamCategoryListResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamCategoryResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamDashboardResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamKeyDto
import com.talkfrly.multiplatform.data.stream.dto.StreamListResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamRequestDto
import com.talkfrly.multiplatform.data.stream.dto.StreamStopResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamViewerResponseDto
import com.talkfrly.multiplatform.domain.stream.StreamCategory
import com.talkfrly.multiplatform.domain.stream.StreamCategoryList
import com.talkfrly.multiplatform.domain.stream.StreamKey
import com.talkfrly.multiplatform.domain.stream.StreamList
import com.talkfrly.multiplatform.domain.stream.StreamRequest
import com.talkfrly.multiplatform.domain.stream.StreamResponse
import com.talkfrly.multiplatform.domain.stream.StreamStopResponse
import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

fun StreamRequest.toDto(): StreamRequestDto = StreamRequestDto(
    name = name,
    category = category,
    avatarUrl = avatarUrl,
    threadId = threadId,
)

fun StreamDashboardResponseDto.toDomain(): StreamResponse = StreamResponse(
    id = id,
    name = name,
    ownerId = ownerId,
    displayName = displayName,
    category = category,
    avatarUrl = avatarUrl,
    isLive = isLive,
    playbackUrl = playbackUrl,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun StreamViewerResponseDto.toDomain(): StreamViewerResponse = StreamViewerResponse(
    id = id,
    name = name,
    ownerId = ownerId,
    displayName = displayName,
    category = category,
    avatarUrl = avatarUrl,
    thumbnailUrl = thumbnailUrl,
    isLive = isLive,
    playbackUrl = playbackUrl,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
)

fun StreamListResponseDto.toDomain(): StreamList = StreamList(
    items = items.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun StreamKeyDto.toDomain(): StreamKey = StreamKey(
    rtmpUrl = rtmpUrl,
    streamKey = streamKey,
    playbackUrl = playbackUrl,
)

fun StreamStopResponseDto.toDomain(): StreamStopResponse = StreamStopResponse(
    message = message,
)

fun StreamCategoryResponseDto.toDomain(): StreamCategory = StreamCategory(
    id = id,
    name = name,
    slug = slug,
    coverUrl = coverUrl,
)

fun StreamCategoryListResponseDto.toDomain(): StreamCategoryList = StreamCategoryList(
    items = items.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)