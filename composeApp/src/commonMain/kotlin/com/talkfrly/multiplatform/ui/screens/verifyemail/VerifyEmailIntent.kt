package com.talkfrly.multiplatform.ui.screens.verifyemail

sealed class VerifyEmailIntent {
    data class UpdateCode(val value: String) : VerifyEmailIntent()
    data object VerifyCode : VerifyEmailIntent()
    data object ResendCode : VerifyEmailIntent()
    data class UpdateMessage(val value: String?) : VerifyEmailIntent()
}