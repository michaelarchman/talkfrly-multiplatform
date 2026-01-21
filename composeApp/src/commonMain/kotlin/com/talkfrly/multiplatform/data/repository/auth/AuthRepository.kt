package com.talkfrly.multiplatform.data.repository.auth

import com.talkfrly.multiplatform.data.api.AuthApi
import com.talkfrly.multiplatform.data.dto.LoginRequestDto
import com.talkfrly.multiplatform.data.dto.RegisterRequestDto
import com.talkfrly.multiplatform.data.mapper.toDomain
import com.talkfrly.multiplatform.data.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.models.DataError
import com.talkfrly.multiplatform.domain.models.LoginRequest
import com.talkfrly.multiplatform.domain.models.LoginResponse
import com.talkfrly.multiplatform.domain.models.RegisterRequest
import com.talkfrly.multiplatform.domain.models.RegisterResponse
import com.talkfrly.multiplatform.domain.models.User
import com.talkfrly.multiplatform.domain.models.VerifyEmailResponse
import com.talkfrly.multiplatform.domain.models.ResendVerificationResponse

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