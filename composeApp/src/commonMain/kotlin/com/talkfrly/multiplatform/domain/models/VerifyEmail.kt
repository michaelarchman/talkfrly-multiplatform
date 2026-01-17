package com.talkfrly.multiplatform.domain.models

data class VerifyEmailResponse(
    val message: String,
    val user: User,
)

data class ResendVerificationResponse(
    val message: String,
)