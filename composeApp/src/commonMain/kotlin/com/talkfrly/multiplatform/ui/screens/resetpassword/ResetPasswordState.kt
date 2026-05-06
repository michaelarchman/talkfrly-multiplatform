package com.talkfrly.multiplatform.ui.screens.resetpassword

data class ResetPasswordState(
    val email: String = "",
    val code: String = "",
    val newPassword: String = "",
    val confirmPassword: String = "",
    val message: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val resendLoading: Boolean = false,
    val resendCooldown: Int = 0,
)
