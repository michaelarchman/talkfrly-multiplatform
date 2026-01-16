package com.talkfrly.multiplatform.ui.session

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SessionViewModel(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state

    init { checkSession() }

    fun checkSession() {
        viewModelScope.launch {
            val token = preferencesRepository.getAccessToken().first()
            println("SESSION - token.length: ${token?.length}")

            _state.value = if (token.isNullOrEmpty()) {
                println("SESSION - checkSession: LoggedOut, token: ${token?.length}")
                SessionState.LoggedOut
            } else {
                println("SESSION - checkSession: LoggedIn, token: ${token.length}")
                SessionState.LoggedIn
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesRepository.clearPreferences()
            println("SESSION - logout: LoggedOut")
            _state.value = SessionState.LoggedOut
//            showTopSnackbar(
//                type = TopSnackbarType.INFO,
//                message = "Wylogowano pomyślnie"
//            )
        }
    }
}
