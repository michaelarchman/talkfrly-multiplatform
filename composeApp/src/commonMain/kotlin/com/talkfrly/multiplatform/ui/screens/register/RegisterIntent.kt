package com.talkfrly.multiplatform.ui.screens.register

sealed class RegisterIntent {
    data object CreateAccount : RegisterIntent()
    data class UpdateFieldEmail(val value: String) : RegisterIntent()
    data class UpdateFieldPassword(val value: String) : RegisterIntent()
    data class UpdateFieldConfirmPassword(val value: String) : RegisterIntent()
    data class UpdateFieldDisplayName(val value: String) : RegisterIntent()
    data class UpdateFieldMessage(val value: String) : RegisterIntent()
}