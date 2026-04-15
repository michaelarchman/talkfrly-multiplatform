package com.talkfrly.multiplatform.ui.screens.account

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.data.user.repository.UserRepository
import com.talkfrly.multiplatform.data.userPreferences.UserPreferencesRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPreferencesRepository: UserPreferencesRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> get() = _state

    fun onIntent(intent: AccountIntent, onLogoutSuccess: (() -> Unit)? = null) {
        when (intent) {
            is AccountIntent.Logout -> logout(onLogoutSuccess)
            is AccountIntent.GetUser -> fetchUser()
            is AccountIntent.SetUserName -> setUserName( intent.value )
            is AccountIntent.GetUserPreferences -> fetchPreferences()
        }
    }

    private fun logout(onLogoutSuccess: (() -> Unit)?) = viewModelScope.launch {
        try {
            startLoading()

            authRepository.logout()
                .onSuccess {
                    println("AccountViewModel - logout: Success")
                    onLogoutSuccess?.invoke()
                }
                .onError { error ->
                    println("AccountViewModel - logout error: ${error.message}")
                }
        } finally {
            stopLoading()
        }
    }

    private fun fetchUser() = viewModelScope.launch {
        startLoading()
        userRepository.getCurrentUser()
            .onSuccess { user ->
                _state.update {
                    it.copy(
                        user = user,
                        userNameInput = user.displayName
                    )
                }
            }
            .onError { err ->
                _state.update{
                    it.copy(error = err.error)
                }
            }
            .onFinally { stopLoading() }
    }

    private fun setUserName(name: String){
        _state.update {
            it.copy(userNameInput = name)
        }

    }

    private fun fetchPreferences() = viewModelScope.launch{
        startLoading()
        userPreferencesRepository.getUserPreferences()
            .onSuccess { preferences ->
                _state.update{
                    it.copy(
                        userPreferences = preferences,
                    )
                }
            }
            .onError { err ->
                _state.update{
                    it.copy(error = err.error)
                }
            }
            .onFinally { stopLoading() }
    }
}