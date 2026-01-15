package com.talkfrly.multiplatform.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id") val id: String,
    @SerialName("email") val email: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("avatar_url") val avatarUrl: String,
    @SerialName("is_verified") val isVerified: Boolean,
    @SerialName("is_admin") val isAdmin: Boolean,
    @SerialName("is_approved") val isApproved: Boolean,
)