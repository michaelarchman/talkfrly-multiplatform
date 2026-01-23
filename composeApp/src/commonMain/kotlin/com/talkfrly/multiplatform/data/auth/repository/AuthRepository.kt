package com.talkfrly.multiplatform.data.auth.repository

import com.talkfrly.multiplatform.data.auth.api.AuthApi
import com.talkfrly.multiplatform.data.auth.mapper.toDomain
import com.talkfrly.multiplatform.data.auth.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.auth.LoginRequest
import com.talkfrly.multiplatform.domain.auth.LoginResponse
import com.talkfrly.multiplatform.domain.auth.RegisterRequest
import com.talkfrly.multiplatform.domain.auth.RegisterResponse
import com.talkfrly.multiplatform.domain.auth.User
import com.talkfrly.multiplatform.domain.auth.VerifyEmailResponse
import com.talkfrly.multiplatform.domain.auth.ResendVerificationResponse

interface AuthRepository  {
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote>
    suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponse, DataError.Remote>
    suspend fun getCurrentUser(): DataResult<User, DataError.Remote>
    suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponse, DataError.Remote>
    suspend fun resendVerification(email: String): DataResult<ResendVerificationResponse, DataError.Remote>
    suspend fun logout(): DataResult<Unit, DataError.Remote>
}

class AuthRepositoryImpl(
    private val api: AuthApi
): AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote> {
        return api
            .login(loginRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponse, DataError.Remote> {
        return api
            .register(registerRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun getCurrentUser(): DataResult<User, DataError.Remote> {
        return api
            .getCurrentUser()
            .map { it.toDomain() }
    }

    override suspend fun verifyEmail(email: String, code: String): DataResult<VerifyEmailResponse, DataError.Remote> {
        return api
            .verifyEmail(email, code)
            .map { it.toDomain() }
    }

    override suspend fun resendVerification(email: String): DataResult<ResendVerificationResponse, DataError.Remote> {
        return api
            .resendVerification(email)
            .map { it.toDomain() }
    }

    override suspend fun logout(): DataResult<Unit, DataError.Remote> {
        return api.logout()
    }
}