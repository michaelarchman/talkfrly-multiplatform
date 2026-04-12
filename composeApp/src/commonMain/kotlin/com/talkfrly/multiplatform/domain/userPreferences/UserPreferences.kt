package com.talkfrly.multiplatform.domain.userPreferences

data class UserPreferences (
    val userId: String,
    val displayType: String,
    val appLanguage: String,
    val defaultAnonymous: Boolean
)