package com.talkfrly.multiplatform.ui.screens.error

sealed class ErrorIntent {
    data object onGoBack: ErrorIntent()
}