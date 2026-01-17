package com.talkfrly.multiplatform.ui.screens.verifyemail

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VerifyEmailViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(VerifyEmailState())
    val state: StateFlow<VerifyEmailState> get() = _state

    fun initializeEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun onIntent(intent: VerifyEmailIntent) {
        when (intent) {
            is VerifyEmailIntent.UpdateCode -> {
                val filtered = intent.value.filter { it.isDigit() }.take(6)
                _state.update { it.copy(code = filtered) }
            }
            is VerifyEmailIntent.VerifyCode -> verifyEmail()
            is VerifyEmailIntent.ResendCode -> resendCode()
            is VerifyEmailIntent.UpdateMessage -> _state.update { it.copy(message = intent.value) }
        }
    }

    private fun verifyEmail() = viewModelScope.launch {
        val email = _state.value.email
        val code = _state.value.code

        if (code.length != 6) {
            _state.update { it.copy(message = "Code must be 6 digits") }
            return@launch
        }

        _state.update { it.copy(isLoading = true, message = null) }

        try {
            authRepository.verifyEmail(email, code)
                .onSuccess { response ->
                    _state.update { it.copy(message = response.message) }
                    // Success - update state to indicate verification complete
                    delay(1500) // Give user time to see success message
                }
                .onError { error ->
                    _state.update { it.copy(message = error.message) }
                }
        } finally {
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun resendCode() = viewModelScope.launch {
        val email = _state.value.email
        val currentCooldown = _state.value.resendCooldown

        if (currentCooldown > 0) {
            return@launch
        }

        _state.update { it.copy(resendLoading = true, message = null) }

        try {
            authRepository.resendVerification(email)
                .onSuccess { response ->
                    _state.update { it.copy(message = response.message) }
                    startCooldown()
                }
                .onError { error ->
                    _state.update { it.copy(message = error.message) }
                }
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