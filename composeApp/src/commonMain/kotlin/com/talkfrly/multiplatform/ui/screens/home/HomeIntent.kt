package com.talkfrly.multiplatform.ui.screens.home

sealed class HomeIntent {
    data object GetStreams: HomeIntent()
    data class SetSelectedTab(val index: Int): HomeIntent()
}
