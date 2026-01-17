package com.talkfrly.multiplatform.data.mapper

import com.talkfrly.multiplatform.data.dto.VerifyEmailResponseDto
import com.talkfrly.multiplatform.data.dto.ResendVerificationResponseDto
import com.talkfrly.multiplatform.domain.models.VerifyEmailResponse
import com.talkfrly.multiplatform.domain.models.ResendVerificationResponse

fun VerifyEmailResponseDto.toDomain(): VerifyEmailResponse = VerifyEmailResponse(
    user = user.toDomain(),
    message = message,
)

fun ResendVerificationResponseDto.toDomain(): ResendVerificationResponse = ResendVerificationResponse(
    message = message,
)