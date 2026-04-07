package com.talkfrly.multiplatform.data.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailResponseDto(
    @SerialName("message") val message: String,
)

@Serializable
data class ResendVerificationResponseDto(
    @SerialName("message") val message: String,
)