package com.talkfrly.multiplatform.data.repository.auth

import com.talkfrly.multiplatform.data.api.AuthApi
import com.talkfrly.multiplatform.data.mapper.toDomain
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.models.DataError
import com.talkfrly.multiplatform.domain.models.LoginRequest
import com.talkfrly.multiplatform.domain.models.LoginResponse
import com.talkfrly.multiplatform.domain.models.RegisterRequest
import com.talkfrly.multiplatform.domain.models.RegisterResponse
import com.talkfrly.multiplatform.domain.models.User

interface AuthRepository  {
    suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote>
    suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponse, DataError.Remote>
    suspend fun getCurrentUser(): DataResult<User, DataError.Remote>
}

class AuthRepositoryImpl(
    private val api: AuthApi
): AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): DataResult<LoginResponse, DataError.Remote> {
        return api
            .login(loginRequest)
            .map { it.toDomain() }
    }

    override suspend fun register(registerRequest: RegisterRequest): DataResult<RegisterResponse, DataError.Remote> {
        return api
            .register(registerRequest)
            .map { it.toDomain() }
    }

    override suspend fun getCurrentUser(): DataResult<User, DataError.Remote> {
        return api
            .getCurrentUser()
            .map { it.toDomain() }
    }
}