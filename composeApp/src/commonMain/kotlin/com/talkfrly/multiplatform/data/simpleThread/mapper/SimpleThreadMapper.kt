package com.talkfrly.multiplatform.data.simpleThread.mapper

import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2CreateRequestDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2DeleteResponseDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2Dto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2ListRequestDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2ListResponseDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2UpdateRequestDto
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThread
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadCreateRequest
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadDeleteResponse
import com.talkfrly.multiplatform.domain.simpleThread.Thread2ListRequest
import com.talkfrly.multiplatform.domain.simpleThread.Thread2ListResponse
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadUpdateRequest

fun Thread2ListRequest.toDto(): Thread2ListRequestDto = Thread2ListRequestDto(
    page = page,
    limit = limit,
    role = role,
)

fun Thread2ListResponseDto.toDomain(): Thread2ListResponse = Thread2ListResponse(
    threads = threads.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun SimpleThreadCreateRequest.toDto(): Thread2CreateRequestDto = Thread2CreateRequestDto(
    name = name,
    slug = slug,
    description = description,
)

fun Thread2Dto.toDomain(): SimpleThread = SimpleThread(
    id = id,
    name = name,
    slug = slug,
    description = description,
    creatorId = creatorId,
    memberCount = memberCount,
//    dailyPostCount = dailyPostCount,
//    dailyVisitors = dailyVisitors,
//    resolvedCount = resolvedCount,
    isMember = isMember,
    role = role,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun SimpleThreadUpdateRequest.toDto(): Thread2UpdateRequestDto = Thread2UpdateRequestDto(
    name = name,
    description = description,
)

fun Thread2DeleteResponseDto.toDomain(): SimpleThreadDeleteResponse = SimpleThreadDeleteResponse(
    message = message,
)
