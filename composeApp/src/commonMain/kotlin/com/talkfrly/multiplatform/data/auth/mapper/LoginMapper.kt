package com.talkfrly.multiplatform.data.auth.mapper

import com.talkfrly.multiplatform.data.auth.dto.LoginRequestDto
import com.talkfrly.multiplatform.data.auth.dto.LoginResponseDto
import com.talkfrly.multiplatform.data.user.toDomain
import com.talkfrly.multiplatform.domain.auth.LoginRequest
import com.talkfrly.multiplatform.domain.auth.LoginResponse

fun LoginRequestDto.toDomain(): LoginRequest = LoginRequest(
    email = email,
    password = password
)

fun LoginRequest.toDto(): LoginRequestDto = LoginRequestDto(
    email = email,
    password = password
)

fun LoginResponseDto.toDomain(): LoginResponse = LoginResponse(
    message = message,
    accessToken = accessToken,
    refreshToken = refreshToken
)