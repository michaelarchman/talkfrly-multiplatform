package com.talkfrly.multiplatform.data.user.repository

import com.talkfrly.multiplatform.data.user.api.UserApi
import com.talkfrly.multiplatform.data.user.dto.UserUpdateRequestDto
import com.talkfrly.multiplatform.data.user.mapper.toDomain
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.user.User

interface UserRepository {
    suspend fun getCurrentUser(): DataResult<User, DataError.Remote>
    suspend fun updateCurrentUser(userUpdateRequest: UserUpdateRequestDto): DataResult<User, DataError.Remote>
    suspend fun deleteCurrentUser(): DataResult<Unit, DataError.Remote>
}

class UserRepositoryImpl(
    private val api: UserApi
): UserRepository  {
    override suspend fun getCurrentUser(): DataResult<User, DataError.Remote> {
        return api
            .getCurrentUser()
            .map { it.toDomain() }
    }

    override suspend fun updateCurrentUser(userUpdateRequest: UserUpdateRequestDto): DataResult<User, DataError.Remote> {
        return api
            .updateCurrentUser(userUpdateRequest)
            .map { it.toDomain() }
    }

    override suspend fun deleteCurrentUser(): DataResult<Unit, DataError.Remote> {
        return api.deleteCurrentUser()
    }
}