package com.talkfrly.multiplatform.data.auth.mapper

import com.talkfrly.multiplatform.data.auth.dto.VerifyEmailResponseDto
import com.talkfrly.multiplatform.data.auth.dto.ResendVerificationResponseDto
import com.talkfrly.multiplatform.domain.auth.VerifyEmailResponse
import com.talkfrly.multiplatform.domain.auth.ResendVerificationResponse

fun VerifyEmailResponseDto.toDomain(): VerifyEmailResponse = VerifyEmailResponse(
    user = user.toDomain(),
    message = message,
)

fun ResendVerificationResponseDto.toDomain(): ResendVerificationResponse = ResendVerificationResponse(
    message = message,
)