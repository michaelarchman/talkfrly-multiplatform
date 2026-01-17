package com.talkfrly.multiplatform.ui.screens.verifyemail

data class VerifyEmailState(
    val email: String = "",
    val code: String = "",
    val message: String? = null,
    val isLoading: Boolean = false,
    val resendLoading: Boolean = false,
    val resendCooldown: Int = 0,
)