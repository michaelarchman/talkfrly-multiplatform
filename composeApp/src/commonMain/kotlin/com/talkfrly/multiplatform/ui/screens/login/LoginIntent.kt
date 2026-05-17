package com.talkfrly.multiplatform.ui.screens.login

sealed class LoginIntent {
    data object GetAccessToken : LoginIntent()
    data object ToggleRememberMe : LoginIntent()
    data class UpdateFieldUsername(val value: String) : LoginIntent()
    data class UpdateFieldPassword(val value: String) : LoginIntent()
    data class UpdateFieldMessage(val value: String) : LoginIntent()
}