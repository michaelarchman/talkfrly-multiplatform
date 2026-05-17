package com.talkfrly.multiplatform.ui.screens.resetPassword

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ResetPasswordViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ResetPasswordState())
    val state: StateFlow<ResetPasswordState> get() = _state

    fun initializeEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.UpdateCode -> {
                val filtered = intent.value.filter { it.isDigit() }.take(6)
                _state.update { it.copy(code = filtered) }
            }
            is ResetPasswordIntent.UpdateNewPassword -> _state.update { it.copy(newPassword = intent.value) }
            is ResetPasswordIntent.UpdateConfirmPassword -> _state.update { it.copy(confirmPassword = intent.value) }
            is ResetPasswordIntent.UpdateMessage -> _state.update { it.copy(message = intent.value) }
            is ResetPasswordIntent.Submit -> submit()
            is ResetPasswordIntent.ResendCode -> resendCode()
        }
    }

    private fun submit() = viewModelScope.launch {
        val s = _state.value
        if (s.code.length != 6) {
            _state.update { it.copy(message = "Code must be 6 digits") }
            return@launch
        }
        if (s.newPassword.length < 8) {
            _state.update { it.copy(message = "Password must be at least 8 characters") }
            return@launch
        }
        if (s.newPassword != s.confirmPassword) {
            _state.update { it.copy(message = "Passwords do not match") }
            return@launch
        }
        _state.update { it.copy(isLoading = true, message = null) }
        try {
            authRepository.resetPassword(s.email, s.code, s.newPassword)
                .onSuccess {
                    _state.update { it.copy(isSuccess = true) }
                }
                .onError { error ->
                    _state.update { it.copy(message = error.message) }
                }
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun resendCode() = viewModelScope.launch {
        if (_state.value.resendCooldown > 0) return@launch
        _state.update { it.copy(resendLoading = true, message = null) }
        try {
            authRepository.resendResetCode(_state.value.email)
                .onSuccess { startCooldown() }
                .onError { error -> _state.update { it.copy(message = error.message) } }
        } finally {
            _state.update { it.copy(resendLoading = false) }
        }
    }

    private fun startCooldown() {
        _state.update { it.copy(resendCooldown = 60) }
        viewModelScope.launch {
            repeat(60) {
                delay(1000)
                _state.update { state ->
                    state.copy(resendCooldown = maxOf(0, state.resendCooldown - 1))
                }
            }
        }
    }
}
