package com.talkfrly.multiplatform.data.userPreferences

import com.talkfrly.multiplatform.domain.userPreferences.UserPreferences

fun UserPreferencesDto.toDomain(): UserPreferences = UserPreferences(
    displayPreference = displayPreference,
    displayName = displayName,
    language = language,
)
