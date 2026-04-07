package com.talkfrly.multiplatform.ui.screens.profile

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.auth.repository.AuthRepository
import com.talkfrly.multiplatform.data.user.UserRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
): BaseViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> get() = _state

    fun onIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.GetUserId -> {}
            is ProfileIntent.GetUsername -> getUserName()
        }
    }

    private fun getUserName() = viewModelScope.launch {
        startLoading()
        userRepository.getCurrentUser()
            .onSuccess { result -> // User
               _state.update {
                   it.copy(
                       userName = result.displayName ?: ""
                   )
               }
            }.onError { err ->
                _state.update {
                    it.copy(
                        error = err.error
                    )
                }
            }.onFinally {
                stopLoading()
            }
    }
}