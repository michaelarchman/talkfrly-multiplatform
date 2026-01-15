package com.talkfrly.multiplatform.data.api

import com.talkfrly.multiplatform.data.core.BASE_API
import com.talkfrly.multiplatform.data.core.makeFormRequest
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.models.DataError
import com.talkfrly.multiplatform.domain.models.LoginRequest
import com.talkfrly.multiplatform.domain.models.LoginResponse
import io.ktor.client.HttpClient

interface AuthApi {
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote>
}

class AuthApiImpl(
    private val httpClient: HttpClient,
    private val preferencesRepository: PreferencesRepository,
): AuthApi {
    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote> {
        return makeFormRequest(
            httpClient = httpClient,
            url = "$BASE_API/api/v1/auth/login",
            preferencesRepository = preferencesRepository,
            formParameters = mapOf(
                "email" to loginRequest.email,
                "password" to loginRequest.password
            )
        )
    }
}