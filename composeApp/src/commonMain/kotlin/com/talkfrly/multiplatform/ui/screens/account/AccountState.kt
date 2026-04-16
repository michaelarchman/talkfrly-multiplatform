package com.talkfrly.multiplatform.ui.screens.account

import com.talkfrly.multiplatform.domain.user.User
import com.talkfrly.multiplatform.domain.userPreferences.UserPreferences

data class AccountState(
    val user: User? = null,
    val message: String = "Account Settings",
    val error: String? = null,
    val userNameInput: String = "",
    val userPreferences: UserPreferences? = null,
    val memoryCacheSizeBytes: Long? = null,
    val diskCacheSizeBytes: Long? = null,
    val isClearingImageCache: Boolean = false,
)