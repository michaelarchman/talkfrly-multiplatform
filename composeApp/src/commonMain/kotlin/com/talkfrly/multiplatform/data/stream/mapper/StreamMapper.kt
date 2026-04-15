package com.talkfrly.multiplatform.data.stream.mapper

import com.talkfrly.multiplatform.data.stream.dto.StreamRequestDto
import com.talkfrly.multiplatform.data.stream.dto.StreamKeyDto
import com.talkfrly.multiplatform.data.stream.dto.StreamListResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamStopResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamViewerResponseDto
import com.talkfrly.multiplatform.domain.stream.StreamResponse
import com.talkfrly.multiplatform.domain.stream.StreamKey
import com.talkfrly.multiplatform.domain.stream.StreamList
import com.talkfrly.multiplatform.domain.stream.StreamRequest
import com.talkfrly.multiplatform.domain.stream.StreamStopResponse
import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

fun StreamRequestDto.toDomain(): StreamRequest = StreamRequest(
    name = name,
    category = category,
)

fun StreamRequest.toDto(): StreamRequestDto = StreamRequestDto(
    name = name,
    category = category,
)

fun StreamResponseDto.toDomain(): StreamResponse = StreamResponse(
    id = id,
    name = name,
    ownerId = ownerId,
    category = category,
    avatarUrl = avatarUrl,
    thumbnailUrl = thumbnailUrl,
    isLive = isLive,
    playbackUrl = playbackUrl,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun StreamViewerResponseDto.toDomain(): StreamViewerResponse = StreamViewerResponse(
    id = id,
    name = name,
    ownerId = ownerId,
    category = category,
    avatarUrl = avatarUrl,
    thumbnailUrl = thumbnailUrl,
    isLive = isLive,
    playbackUrl = playbackUrl,
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
