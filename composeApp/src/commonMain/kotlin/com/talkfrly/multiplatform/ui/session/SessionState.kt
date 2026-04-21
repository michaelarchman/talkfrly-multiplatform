package com.talkfrly.multiplatform.ui.session

sealed class SessionState {
    data object Loading : SessionState()
    data object LoggedOut : SessionState()
    data object LoggedIn : SessionState()
}