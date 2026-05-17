package com.talkfrly.multiplatform.ui.screens.forgotPassword

data class ForgotPasswordState(
    val email: String = "",
    val message: String? = null,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
)
