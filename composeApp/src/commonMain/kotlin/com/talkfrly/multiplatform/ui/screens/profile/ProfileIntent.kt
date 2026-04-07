package com.talkfrly.multiplatform.ui.screens.profile

sealed class ProfileIntent {
    data object GetUsername : ProfileIntent()
    data object GetUserId : ProfileIntent()
}