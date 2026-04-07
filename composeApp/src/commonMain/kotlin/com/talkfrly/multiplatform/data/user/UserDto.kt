package com.talkfrly.multiplatform.data.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("user_id") val userId: String,
    @SerialName("email") val email: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("is_admin") val isAdmin: Boolean,
//    @SerialName("avatar_url") val avatarUrl: String? = null,
//    @SerialName("is_verified") val isVerified: Boolean,
//    @SerialName("is_approved") val isApproved: Boolean,
)
