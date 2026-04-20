package com.talkfrly.multiplatform.data.userPreferences

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface UserPreferencesApi {
    suspend fun getUserPreferences(): DataResult<UserPreferencesDto, DataError.Remote>
    suspend fun updateUserPreferences(
        request: UserPreferencesUpdateRequestDto,
    ): DataResult<UserPreferencesDto, DataError.Remote>
}

class UserPreferencesApiImpl(
    private val httpClient: HttpClient,
) : UserPreferencesApi {
    override suspend fun getUserPreferences(): DataResult<UserPreferencesDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/preferences",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun updateUserPreferences(
        request: UserPreferencesUpdateRequestDto,
    ): DataResult<UserPreferencesDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/preferences",
            httpMethod = HttpMethod.Put,
            body = request,
        )
    }
}