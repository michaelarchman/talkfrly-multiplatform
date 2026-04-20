package com.talkfrly.multiplatform.data.userPreferences

import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.userPreferences.UserPreferences

interface UserPreferencesRepository {
    suspend fun getUserPreferences(): DataResult<UserPreferences, DataError.Remote>
    suspend fun updateUserPreferences(
        request: UserPreferencesUpdateRequestDto,
    ): DataResult<UserPreferences, DataError.Remote>
}

class UserPreferencesRepositoryImpl(
    private val api: UserPreferencesApi,
): UserPreferencesRepository {
    override suspend fun getUserPreferences(): DataResult<UserPreferences, DataError.Remote> {
        return api
            .getUserPreferences()
            .map { it.toDomain() }
    }

    override suspend fun updateUserPreferences(
        request: UserPreferencesUpdateRequestDto,
    ): DataResult<UserPreferences, DataError.Remote> {
        return api
            .updateUserPreferences(request)
            .map { it.toDomain() }
    }
}