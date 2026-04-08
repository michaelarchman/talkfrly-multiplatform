package com.talkfrly.multiplatform.ui.screens.account

import com.talkfrly.multiplatform.domain.user.User

data class AccountState(
    val user: User? = null,
    val message: String = "Account Settings",
    val error: String? = null
)