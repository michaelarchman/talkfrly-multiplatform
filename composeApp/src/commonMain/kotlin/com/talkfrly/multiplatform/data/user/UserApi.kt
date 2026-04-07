package com.talkfrly.multiplatform.data.user

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface UserApi {
    suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote>
}

class UserApiImpl(
    private val httpClient: HttpClient,
): UserApi {
    override suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/users/me",
            httpMethod = HttpMethod.Get,
        )
    }
}