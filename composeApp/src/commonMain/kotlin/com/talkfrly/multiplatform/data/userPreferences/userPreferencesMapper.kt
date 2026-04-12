package com.talkfrly.multiplatform.data.userPreferences

import com.talkfrly.multiplatform.domain.userPreferences.UserPreferences

fun UserPreferencesDto.toDomain(): UserPreferences = UserPreferences(
    userId = userId,
    displayType = displayType,
    appLanguage = appLanguage,
    defaultAnonymous = defaultAnonymous
)
