package com.talkfrly.multiplatform.domain.models

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val accessToken: String? = null,
    val refreshToken: String? = null,
    val user: User
)