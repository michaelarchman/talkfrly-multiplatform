package com.talkfrly.multiplatform.data.api

import com.talkfrly.multiplatform.data.core.makeFormRequest
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.dto.LoginResponseDto
import com.talkfrly.multiplatform.data.dto.RegisterResponseDto
import com.talkfrly.multiplatform.data.dto.ResendVerificationResponseDto
import com.talkfrly.multiplatform.data.dto.UserDto
import com.talkfrly.multiplatform.data.dto.VerifyEmailResponseDto
import com.talkfrly.multiplatform.data.repository.preferences.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.models.DataError
import com.talkfrly.multiplatform.domain.models.LoginRequest
import com.talkfrly.multiplatform.domain.models.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface AuthApi {
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponseDto, DataError.Remote>
    suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponseDto, DataError.Remote>
    suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote>
    suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponseDto, DataError.Remote>
    suspend fun resendVerification(email: String): DataResult<ResendVerificationResponseDto, DataError.Remote>
}

class AuthApiImpl(
    private val httpClient: HttpClient,
    private val preferencesRepository: PreferencesRepository,
): AuthApi {
    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponseDto, DataError.Remote> {
        return makeFormRequest(
            httpClient = httpClient,
            urlString = "/auth/login",
            preferencesRepository = preferencesRepository,
            formParameters = mapOf(
                "email" to loginRequest.email,
                "password" to loginRequest.password,
            )
        )
    }

    override suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/register",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
            body = registerRequest
        )
    }

    override suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote> {
        return makeRequest(
            requireAuth = true,
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/auth/me",
            preferencesRepository = preferencesRepository,
        )
    }

    override suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/verify-email",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
            body = mapOf(
                "email" to email,
                "code" to code,
            )
        )
    }

    override suspend fun resendVerification(email: String): DataResult<ResendVerificationResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/resend-verification",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
            body = mapOf(
                "email" to email,
            )
        )
    }
}