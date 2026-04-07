package com.talkfrly.multiplatform.domain.auth

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val message: String,
    val accessToken: String? = null,
    val refreshToken: String? = null,
)