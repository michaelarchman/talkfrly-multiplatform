package com.talkfrly.multiplatform.ui.screens.login

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.models.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val preferencesRepository: PreferencesRepository
): BaseViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> get() = _state

    fun onIntent(intent: LoginIntent, onLoginSuccess: (() -> Unit)? = null) {
        when (intent) {
            is LoginIntent.UpdateFieldUsername -> _state.update { it.copy(email = intent.value) }
            is LoginIntent.UpdateFieldPassword -> _state.update { it.copy(password = intent.value) }
            is LoginIntent.UpdateFieldMessage -> _state.update { it.copy(message = intent.value) }
            is LoginIntent.GetAccessToken -> getToken(onLoginSuccess)
       }
    }

    private fun getToken(onLoginSuccess: (() -> Unit)?) = viewModelScope.launch {
        val email = _state.value.email.orEmpty()
        val password = _state.value.password.orEmpty()

        if (email.isBlank() || password.isBlank()) {
            return@launch
        }

        val loginRequest = LoginRequest(
            email = email,
            password = password,
        )

        try {
            startLoading()

            authRepository.login(loginRequest)
                .onSuccess { response ->
                    onLoginSuccess?.invoke()
                    println("authRepository.login -> $response")
                }
                .onError { error ->
                    _state.update { it.copy(message = error.message) }
                }
        } finally {
            stopLoading()
        }
    }
}