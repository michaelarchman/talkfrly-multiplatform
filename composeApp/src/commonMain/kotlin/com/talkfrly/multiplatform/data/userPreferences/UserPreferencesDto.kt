package com.talkfrly.multiplatform.data.userPreferences

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesUpdateRequestDto(
    @SerialName("display_preference") val displayPreference: String,
    @SerialName("language") val language: String,
    @SerialName("display_name") val displayName: String? = null,
)

@Serializable
data class UserPreferencesDto(
    @SerialName("display_preference") val displayPreference: String,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("language") val language: String,
)