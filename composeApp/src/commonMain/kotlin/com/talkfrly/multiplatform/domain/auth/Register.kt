package com.talkfrly.multiplatform.domain.auth

data class RegisterResponse(
    val message: String,
)

data class RegisterRequest(
    val email: String,
    val password: String,
    val displayName: String? = null,
)
