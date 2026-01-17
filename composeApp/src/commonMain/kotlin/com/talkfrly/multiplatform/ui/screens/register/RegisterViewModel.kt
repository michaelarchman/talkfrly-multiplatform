package com.talkfrly.multiplatform.ui.screens.register

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.models.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
): BaseViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> get() = _state

    fun onIntent(intent: RegisterIntent, onRegisterSuccess: (() -> Unit)? = null) {
        when (intent) {
            is RegisterIntent.UpdateFieldEmail -> _state.update { it.copy(email = intent.value) }
            is RegisterIntent.UpdateFieldPassword -> _state.update { it.copy(password = intent.value) }
            is RegisterIntent.UpdateFieldConfirmPassword -> _state.update { it.copy(confirmPassword = intent.value) }
            is RegisterIntent.UpdateFieldDisplayName -> _state.update { it.copy(displayName = intent.value) }
            is RegisterIntent.UpdateFieldMessage -> _state.update { it.copy(message = intent.value) }
            is RegisterIntent.CreateAccount -> createAccount(onRegisterSuccess)
        }
    }

    private fun createAccount(onRegisterSuccess: (() -> Unit)?) = viewModelScope.launch {
        val email = _state.value.email.orEmpty()
        val password = _state.value.password.orEmpty()
        val confirmPassword = _state.value.confirmPassword.orEmpty()
        val displayName = _state.value.displayName

        if (email.isBlank() || password.isBlank()) {
            _state.update { it.copy(message = "Email and password are required") }
            return@launch
        }

        if (!isValidEmail(email)) {
            _state.update { it.copy(message = "Please enter a valid email") }
            return@launch
        }

        if (password != confirmPassword) {
            _state.update { it.copy(message = "Passwords do not match") }
            return@launch
        }

        val registerRequest = RegisterRequest(
            email = email,
            password = password,
            displayName = displayName
        )

        try {
            startLoading()

            authRepository.register(registerRequest)
                .onSuccess { response ->
                    _state.update { it.copy(message = response.message) }
                    onRegisterSuccess?.invoke()
                }
                .onError { error ->
                    _state.update { it.copy(message = error.message) }
                }
        } finally {
            stopLoading()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        return emailRegex.matches(email)
    }
}