package com.talkfrly.multiplatform.ui.session

import com.talkfrly.multiplatform.domain.core.DataError

sealed class SessionState {
    data object Loading : SessionState()
    data object LoggedOut : SessionState()
    data object LoggedIn : SessionState()
    data object Error: SessionState()
}