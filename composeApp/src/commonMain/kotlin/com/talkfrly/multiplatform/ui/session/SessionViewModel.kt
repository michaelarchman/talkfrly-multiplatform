package com.talkfrly.multiplatform.ui.session

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.repository.auth.AuthRepository
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel(
    private val authRepository: AuthRepository,
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state

    init { checkSession() }

    fun checkSession() {
        viewModelScope.launch {
            // Try to get current user - if it succeeds (200), user is logged in
            authRepository.getCurrentUser()
                .onSuccess { user ->
                    println("SESSION - checkSession: LoggedIn, user: ${user.email}")
                    _state.value = SessionState.LoggedIn
                }
                .onError { error ->
                    // If it fails (401 or other error), user is logged out
                    println("SESSION - checkSession: LoggedOut, error: ${error.message}, code: ${error.code}")
                    _state.value = SessionState.LoggedOut
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesRepository.clearPreferences()
            println("SESSION - logout: LoggedOut")
            _state.value = SessionState.LoggedOut
        }
    }
}
