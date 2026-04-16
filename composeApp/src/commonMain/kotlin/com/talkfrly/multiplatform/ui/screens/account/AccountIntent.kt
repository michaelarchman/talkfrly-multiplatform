package com.talkfrly.multiplatform.ui.screens.account

sealed class AccountIntent {
    data object Logout : AccountIntent()
    data object GetUser: AccountIntent()
    data class SetUserName( val value: String): AccountIntent()
    data object GetUserPreferences: AccountIntent()
    data object RefreshImageCacheStats : AccountIntent()
    data object ClearImageCache : AccountIntent()
}