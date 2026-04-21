package com.talkfrly.multiplatform.ui.session

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SessionViewModel(
    private val preferencesRepository: PreferencesRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow<SessionState>(SessionState.Loading)
    val state: StateFlow<SessionState> = _state

    init { observeSession() }

    private fun observeSession() {
        viewModelScope.launch {
            preferencesRepository.getAccessToken().collect { token ->
                _state.value = if (token.isNullOrEmpty()) {
                    SessionState.LoggedOut
                } else {
                    SessionState.LoggedIn
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            preferencesRepository.clearAccessToken()
//            showTopSnackbar(
//                type = TopSnackbarType.INFO,
//                message = "Wylogowano pomyślnie"
//            )
        }
    }
}
