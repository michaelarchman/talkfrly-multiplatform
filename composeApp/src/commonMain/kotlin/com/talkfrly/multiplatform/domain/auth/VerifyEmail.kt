package com.talkfrly.multiplatform.domain.auth

data class VerifyEmailResponse(
    val message: String,
    val user: User,
)

data class ResendVerificationResponse(
    val message: String,
)