package com.talkfrly.multiplatform.data.userPreferences

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferencesDto (
    @SerialName("user_id") val userId: String,
    @SerialName("display_type") val displayType: String,
    @SerialName("app_language") val appLanguage: String,
    @SerialName("default_anonymous") val defaultAnonymous: Boolean
    )