package com.talkfrly.multiplatform.data.preferences.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.http.Cookie
import io.ktor.http.Url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

interface PreferencesRepository : CookiesStorage {
    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)
    suspend fun clearAccessToken()

    fun getRefreshToken(): Flow<String?>
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun clearPreferences()
}

class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferenceKeys {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.ACCESS_TOKEN]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[PreferenceKeys.ACCESS_TOKEN] = token
        }
    }

    override fun getRefreshToken(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.REFRESH_TOKEN]
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit {
            it[PreferenceKeys.REFRESH_TOKEN] = refreshToken
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { it.clear() }
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        when (cookie.name) {
            "access_token" -> saveAccessToken(cookie.value)
            "refresh_token" -> saveRefreshToken(cookie.value)
        }
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        val cookies = mutableListOf<Cookie>()

        getAccessToken().firstOrNull()?.let {
            cookies.add(Cookie("access_token", it))
        }
        getRefreshToken().firstOrNull()?.let {
            cookies.add(Cookie("refresh_token", it))
        }

        return cookies
    }

    override suspend fun clearAccessToken() {
        dataStore.edit {
            it.remove(PreferenceKeys.ACCESS_TOKEN)
        }
    }

    override fun close() {}
}