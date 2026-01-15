package com.talkfrly.multiplatform.domain.models

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val user: User
)