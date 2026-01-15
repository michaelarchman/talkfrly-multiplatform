package com.talkfrly.multiplatform.data.repository.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface PreferencesRepository {
    fun getToken(): Flow<String?>
    suspend fun saveToken(token: String)

    fun getRefreshToken(): Flow<String?>
    suspend fun saveRefreshToken(refreshToken: String)

    fun getServerUrl(): Flow<String?>
    suspend fun saveServerUrl(serverUrl: String)

    fun getWeekRangeLength(): Flow<String?>
    suspend fun saveWeekRangeLength(length: String)

    suspend fun clearPreferences()
}

class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    private object PreferenceKeys {
        val TOKEN = stringPreferencesKey("token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val SERVER_URL = stringPreferencesKey("server_url")
        val WEEK_RANGE_LENGTH = stringPreferencesKey("week_range_length")
    }

    override fun getToken(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.TOKEN]
        }
    }

    override suspend fun saveToken(token: String) {
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

    override fun getServerUrl(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.SERVER_URL]
        }
    }

    override suspend fun saveServerUrl(serverUrl: String) {
        dataStore.edit {
            it[PreferenceKeys.SERVER_URL] = serverUrl
        }
    }

    override fun getWeekRangeLength(): Flow<String?> {
        return dataStore.data.map {
            it[PreferenceKeys.WEEK_RANGE_LENGTH]
        }
    }

    override suspend fun saveWeekRangeLength(length: String) {
        dataStore.edit {
            it[PreferenceKeys.WEEK_RANGE_LENGTH] = length
        }
    }

    override suspend fun clearPreferences() {
        dataStore.edit { it.clear() }
    }
}