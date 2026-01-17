package com.talkfrly.multiplatform.data.mapper

import com.talkfrly.multiplatform.data.dto.RegisterRequestDto
import com.talkfrly.multiplatform.data.dto.RegisterResponseDto
import com.talkfrly.multiplatform.domain.models.RegisterRequest
import com.talkfrly.multiplatform.domain.models.RegisterResponse

fun RegisterRequestDto.toDomain(): RegisterRequest = RegisterRequest(
    email = email,
    password = password,
    displayName = displayName,
)

fun RegisterRequest.toDto(): RegisterRequestDto = RegisterRequestDto(
    email = email,
    password = password,
    displayName = displayName ?: ""
)

fun RegisterResponseDto.toDomain(): RegisterResponse = RegisterResponse(
    user = user.toDomain(),
    message = message,
)