package com.talkfrly.multiplatform.data.auth.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.auth.dto.LoginRequestDto
import com.talkfrly.multiplatform.data.auth.dto.LoginResponseDto
import com.talkfrly.multiplatform.data.auth.dto.RegisterRequestDto
import com.talkfrly.multiplatform.data.auth.dto.RegisterResponseDto
import com.talkfrly.multiplatform.data.auth.dto.ResendVerificationResponseDto
import com.talkfrly.multiplatform.data.auth.dto.UserDto
import com.talkfrly.multiplatform.data.auth.dto.VerifyEmailResponseDto
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.DataError
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface AuthApi {
    suspend fun login(loginRequestDto: LoginRequestDto): DataResult<LoginResponseDto, DataError.Remote>
    suspend fun register(registerRequestDto: RegisterRequestDto): DataResult<RegisterResponseDto, DataError.Remote>
    suspend fun getCurrentUser(): DataResult<UserDto, DataError.Remote>
    suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponseDto, DataError.Remote>
    suspend fun resendVerification(email: String): DataResult<ResendVerificationResponseDto, DataError.Remote>
    suspend fun logout(): DataResult<Unit, DataError.Remote>
}

class AuthApiImpl(
    private val httpClient: HttpClient,
    private val preferencesRepository: PreferencesRepository,
): AuthApi {
    override suspend fun login(loginRequestDto: LoginRequestDto): DataResult<LoginResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/login",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
            body = loginRequestDto
        )
    }

    override suspend fun register(registerRequestDto: RegisterRequestDto): DataResult<RegisterResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/register",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
            body = registerRequestDto
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

    override suspend fun logout(): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/logout",
            httpMethod = HttpMethod.Post,
            preferencesRepository = preferencesRepository,
        )
    }
}