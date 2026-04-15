package com.talkfrly.multiplatform.data.user.api

import com.multiplatform.webview.web.WebContent
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.user.dto.UserDto
import com.talkfrly.multiplatform.data.user.dto.UserUpdateRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface UserApi {
    suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote>
    suspend fun updateCurrentUser(userUpdateRequest: UserUpdateRequestDto): DataResult<UserDto, DataError.Remote>
    suspend fun deleteCurrentUser(): DataResult<Unit, DataError.Remote>
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

    override suspend fun updateCurrentUser(userUpdateRequest: UserUpdateRequestDto): DataResult<UserDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/users/me",
            httpMethod = HttpMethod.Put,
            body = listOf(
                "display_name" to userUpdateRequest.displayName,
                "avatar_url" to userUpdateRequest.avatarUrl
            )
        )
    }

    override suspend fun deleteCurrentUser(): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/users/me",
            httpMethod = HttpMethod.Delete,
        )
    }
}