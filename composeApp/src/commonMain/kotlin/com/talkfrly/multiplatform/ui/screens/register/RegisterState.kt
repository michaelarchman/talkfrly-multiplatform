package com.talkfrly.multiplatform.ui.screens.register

data class RegisterState(
    val email: String? = "",
    val password: String? = "",
    val confirmPassword: String? = "",
    val displayName: String? = "",
    val message: String? = null,
)