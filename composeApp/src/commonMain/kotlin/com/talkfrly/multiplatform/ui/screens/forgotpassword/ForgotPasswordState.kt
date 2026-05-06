package com.talkfrly.multiplatform.ui.screens.forgotpassword

data class ForgotPasswordState(
    val email: String = "",
    val message: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)
