package com.talkfrly.multiplatform.ui.screens.account

sealed class AccountIntent {
    data object Logout : AccountIntent()
}