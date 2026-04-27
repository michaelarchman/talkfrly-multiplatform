package com.talkfrly.multiplatform.data.simpleThread.repository

import com.talkfrly.multiplatform.data.simpleThread.api.Thread2Api
import com.talkfrly.multiplatform.data.simpleThread.mapper.toDomain
import com.talkfrly.multiplatform.data.simpleThread.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThread
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadCreateRequest
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadDeleteResponse
import com.talkfrly.multiplatform.domain.simpleThread.Thread2ListRequest
import com.talkfrly.multiplatform.domain.simpleThread.Thread2ListResponse
import com.talkfrly.multiplatform.domain.simpleThread.SimpleThreadUpdateRequest

interface Thread2Repository {
    suspend fun getThreads(request: Thread2ListRequest): DataResult<Thread2ListResponse, DataError.Remote>
    suspend fun createThread(request: SimpleThreadCreateRequest): DataResult<SimpleThread, DataError.Remote>
    suspend fun getThreadById(id: String): DataResult<SimpleThread, DataError.Remote>
    suspend fun updateThread(id: String, request: SimpleThreadUpdateRequest): DataResult<SimpleThread, DataError.Remote>
    suspend fun deleteThread(id: String): DataResult<SimpleThreadDeleteResponse, DataError.Remote>
}

class Thread2RepositoryImpl(
    private val api: Thread2Api,
) : Thread2Repository {
    override suspend fun getThreads(request: Thread2ListRequest): DataResult<Thread2ListResponse, DataError.Remote> {
        return api
            .getThreads(request.toDto())
            .map { it.toDomain() }
    }

    override suspend fun createThread(request: SimpleThreadCreateRequest): DataResult<SimpleThread, DataError.Remote> {
        return api
            .createThread(request.toDto())
            .map { it.toDomain() }
    }

    override suspend fun getThreadById(id: String): DataResult<SimpleThread, DataError.Remote> {
        return api
            .getThreadById(id)
            .map { it.toDomain() }
    }

    override suspend fun updateThread(id: String, request: SimpleThreadUpdateRequest): DataResult<SimpleThread, DataError.Remote> {
        return api
            .updateThread(id, request.toDto())
            .map { it.toDomain() }
    }

    override suspend fun deleteThread(id: String): DataResult<SimpleThreadDeleteResponse, DataError.Remote> {
        return api
            .deleteThread(id)
            .map { it.toDomain() }
    }
}
