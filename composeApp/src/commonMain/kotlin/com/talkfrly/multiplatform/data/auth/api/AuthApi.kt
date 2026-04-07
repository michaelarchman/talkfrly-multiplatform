package com.talkfrly.multiplatform.data.auth.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.auth.dto.LoginRequestDto
import com.talkfrly.multiplatform.data.auth.dto.LoginResponseDto
import com.talkfrly.multiplatform.data.auth.dto.RegisterRequestDto
import com.talkfrly.multiplatform.data.auth.dto.RegisterResponseDto
import com.talkfrly.multiplatform.data.auth.dto.ResendVerificationResponseDto
import com.talkfrly.multiplatform.data.auth.dto.VerifyEmailResponseDto
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.DataError
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface AuthApi {
    suspend fun login(loginRequestDto: LoginRequestDto): DataResult<LoginResponseDto, DataError.Remote>
    suspend fun register(registerRequestDto: RegisterRequestDto): DataResult<RegisterResponseDto, DataError.Remote>
    suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponseDto, DataError.Remote>
    suspend fun resendVerification(email: String): DataResult<ResendVerificationResponseDto, DataError.Remote>
    suspend fun logout(): DataResult<Unit, DataError.Remote>
}

class AuthApiImpl(
    private val httpClient: HttpClient,
): AuthApi {
    override suspend fun login(loginRequestDto: LoginRequestDto): DataResult<LoginResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/login",
            httpMethod = HttpMethod.Post,
            body = loginRequestDto
        )
    }

    override suspend fun register(registerRequestDto: RegisterRequestDto): DataResult<RegisterResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/register",
            httpMethod = HttpMethod.Post,
            body = registerRequestDto
        )
    }

    override suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/auth/verify-email",
            httpMethod = HttpMethod.Post,
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
        )
    }
}