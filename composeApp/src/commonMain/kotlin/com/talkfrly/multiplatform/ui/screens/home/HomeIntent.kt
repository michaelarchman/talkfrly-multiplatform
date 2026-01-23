package com.talkfrly.multiplatform.ui.screens.home

sealed class HomeIntent {
    data object Logout: HomeIntent()
    data object GetPublications: HomeIntent()
}
