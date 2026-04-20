package com.talkfrly.multiplatform.domain.userPreferences

data class UserPreferences(
    val displayPreference: String,
    val displayName: String? = null,
    val language: String,
)