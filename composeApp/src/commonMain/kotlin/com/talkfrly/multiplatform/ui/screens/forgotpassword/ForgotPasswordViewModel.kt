package com.talkfrly.multiplatform.ui.screens.forgotpassword

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    val state: StateFlow<ForgotPasswordState> get() = _state

    fun onIntent(intent: ForgotPasswordIntent) {
        when (intent) {
            is ForgotPasswordIntent.UpdateEmail -> _state.update { it.copy(email = intent.value) }
            is ForgotPasswordIntent.Submit -> submit()
            is ForgotPasswordIntent.UpdateMessage -> _state.update { it.copy(message = intent.value) }
        }
    }

    private fun submit() = viewModelScope.launch {
        val email = _state.value.email.trim()
        if (email.isEmpty()) {
            _state.update { it.copy(message = "Please enter your email") }
            return@launch
        }
        _state.update { it.copy(isLoading = true, message = null) }
        try {
            authRepository.forgotPassword(email)
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
}
