package com.talkfrly.multiplatform.data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferencesRepository {
    fun getAccessToken(): Flow<String?>
    suspend fun saveAccessToken(token: String)

    fun getRefreshToken(): Flow<String?>
    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun clearPreferences()
}

class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferenceKeys {
        val TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
    }

    override fun getAccessToken(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.TOKEN]
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit {
            it[PreferenceKeys.TOKEN] = token
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
}