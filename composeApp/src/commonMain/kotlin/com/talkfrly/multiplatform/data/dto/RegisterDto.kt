package com.talkfrly.multiplatform.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequestDto(
    @SerialName("email") val email: String,
    @SerialName("password") val password: String,
    @SerialName("displayName") val displayName: String,
)

@Serializable
data class RegisterResponseDto(
    @SerialName("user") val user: UserDto,
    @SerialName("message") val message: String,
)