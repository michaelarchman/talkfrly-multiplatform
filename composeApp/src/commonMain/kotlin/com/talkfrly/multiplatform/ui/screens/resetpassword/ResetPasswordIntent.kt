package com.talkfrly.multiplatform.ui.screens.resetpassword

sealed class ResetPasswordIntent {
    data class UpdateCode(val value: String) : ResetPasswordIntent()
    data class UpdateNewPassword(val value: String) : ResetPasswordIntent()
    data class UpdateConfirmPassword(val value: String) : ResetPasswordIntent()
    data class UpdateMessage(val value: String?) : ResetPasswordIntent()
    data object Submit : ResetPasswordIntent()
    data object ResendCode : ResetPasswordIntent()
}
