package com.talkfrly.multiplatform.data.threads.repository

import com.talkfrly.multiplatform.data.threads.api.ThreadApi
import com.talkfrly.multiplatform.data.threads.mapper.toDomain
import com.talkfrly.multiplatform.data.threads.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.thread.Thread
import com.talkfrly.multiplatform.domain.thread.ThreadCreateRequest
import com.talkfrly.multiplatform.domain.thread.ThreadListRequest
import com.talkfrly.multiplatform.domain.thread.ThreadListResponse
import com.talkfrly.multiplatform.domain.thread.UpdateThreadRequest

interface ThreadRepository {
    suspend fun threadList(threadListRequest: ThreadListRequest): DataResult<ThreadListResponse, DataError.Remote>
    suspend fun createThread(threadCreateRequest: ThreadCreateRequest): DataResult<Thread, DataError.Remote>
    suspend fun getThreadById(id: String): DataResult<Thread, DataError.Remote>
    suspend fun updateThread(id: String, updateThreadRequest: UpdateThreadRequest): DataResult<Thread, DataError.Remote>
    suspend fun deleteThread(id: String): DataResult<Unit, DataError.Remote>
    suspend fun getThreadBySlug(slug: String): DataResult<Thread, DataError.Remote>
    suspend fun joinThread(id: String): DataResult<Unit, DataError.Remote>
    suspend fun leaveThread(id: String): DataResult<Unit, DataError.Remote>
}

class ThreadRepositoryImpl(
    private val api: ThreadApi,
) : ThreadRepository {
    override suspend fun threadList(threadListRequest: ThreadListRequest): DataResult<ThreadListResponse, DataError.Remote> {
        return api
            .threadList(threadListRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun createThread(threadCreateRequest: ThreadCreateRequest): DataResult<Thread, DataError.Remote> {
        return api
            .createThread(threadCreateRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun getThreadById(id: String): DataResult<Thread, DataError.Remote> {
        return api
            .getThreadById(id)
            .map { it.toDomain() }
    }

    override suspend fun updateThread(
        id: String,
        updateThreadRequest: UpdateThreadRequest
    ): DataResult<Thread, DataError.Remote> {
        return api
            .updateThread(id, updateThreadRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun deleteThread(id: String): DataResult<Unit, DataError.Remote> {
        return api
            .deleteThread(id)
    }

    override suspend fun getThreadBySlug(slug: String): DataResult<Thread, DataError.Remote> {
        return api
            .getThreadBySlug(slug)
            .map { it.toDomain() }
    }

    override suspend fun joinThread(id: String): DataResult<Unit, DataError.Remote> {
        return api.joinThread(id)
    }

    override suspend fun leaveThread(id: String): DataResult<Unit, DataError.Remote> {
        return api
            .leaveThread(id)
    }
}