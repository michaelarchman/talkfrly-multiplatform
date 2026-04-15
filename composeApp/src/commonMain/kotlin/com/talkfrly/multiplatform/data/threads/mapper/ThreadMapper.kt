package com.talkfrly.multiplatform.data.threads.mapper

import com.talkfrly.multiplatform.data.threads.dto.ThreadCreateRequestDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadListRequestDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadListResponseDto
import com.talkfrly.multiplatform.data.threads.dto.UpdateThreadRequestDto
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.domain.thread.ThreadCreateRequest
import com.talkfrly.multiplatform.domain.thread.ThreadListRequest
import com.talkfrly.multiplatform.domain.thread.ThreadListResponse
import com.talkfrly.multiplatform.domain.thread.UpdateThreadRequest

fun ThreadListRequestDto.toDomain(): ThreadListRequest = ThreadListRequest(
    page = page,
    limit = limit,
    role = role,
)

fun ThreadListRequest.toDto(): ThreadListRequestDto = ThreadListRequestDto(
    page = page,
    limit = limit,
    role = role,
)

fun ThreadListResponseDto.toDomain(): ThreadListResponse = ThreadListResponse(
    threads = threads.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun ThreadCreateRequestDto.toDomain(): ThreadCreateRequest = ThreadCreateRequest(
    name = name,
    slug = slug,
    description = description,
)

fun ThreadCreateRequest.toDto(): ThreadCreateRequestDto = ThreadCreateRequestDto(
    name = name,
    slug = slug,
    description = description,
)

fun ThreadDto.toDomain(): Thread = Thread(
    id = id,
    name = name,
    slug = slug,
    description = description,
    creatorId = creatorId,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun UpdateThreadRequestDto.toDomain(): UpdateThreadRequest = UpdateThreadRequest(
    name = name,
    description = description,
)

fun UpdateThreadRequest.toDto(): UpdateThreadRequestDto = UpdateThreadRequestDto(
    name = name,
    description = description,
)
