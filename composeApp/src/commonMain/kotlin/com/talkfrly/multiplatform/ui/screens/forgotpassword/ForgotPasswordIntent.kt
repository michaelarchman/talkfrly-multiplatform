package com.talkfrly.multiplatform.ui.screens.forgotpassword

sealed class ForgotPasswordIntent {
    data class UpdateEmail(val value: String) : ForgotPasswordIntent()
    data object Submit : ForgotPasswordIntent()
    data class UpdateMessage(val value: String?) : ForgotPasswordIntent()
}
