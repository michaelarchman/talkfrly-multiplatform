package com.talkfrly.multiplatform.data.mapper

import com.talkfrly.multiplatform.data.dto.LoginRequestDto
import com.talkfrly.multiplatform.data.dto.LoginResponseDto
import com.talkfrly.multiplatform.domain.models.LoginRequest
import com.talkfrly.multiplatform.domain.models.LoginResponse

fun LoginRequestDto.toDomain(): LoginRequest = LoginRequest(
    email = email,
    password = password
)

fun LoginResponseDto.toDomain(): LoginResponse = LoginResponse(
    user = user.toDomain(),
    accessToken = accessToken,
    refreshToken = refreshToken
)