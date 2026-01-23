package com.talkfrly.multiplatform.data.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
)

@Serializable
data class LoginResponseDto(
    @SerialName("access_token") val accessToken: String? = null,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("user") val user: UserDto,
)