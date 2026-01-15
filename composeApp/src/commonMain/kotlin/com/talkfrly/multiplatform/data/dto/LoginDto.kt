package com.talkfrly.multiplatform.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDto(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
)

data class LoginResponseDto(
    @SerialName("access_token") val accessToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("user") val user: UserDto,
)