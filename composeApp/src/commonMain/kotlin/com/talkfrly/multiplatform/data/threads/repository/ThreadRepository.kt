package com.talkfrly.multiplatform.data.threads.repository

import com.talkfrly.multiplatform.data.threads.api.EmptyResponse
import com.talkfrly.multiplatform.data.threads.api.ThreadApi
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult

interface ThreadRepository {
    suspend fun joinThread(threadId: String): DataResult<EmptyResponse, DataError.Remote>
}

class ThreadRepositoryImpl(
    private val api: ThreadApi,
) : ThreadRepository {
    override suspend fun joinThread(threadId: String): DataResult<EmptyResponse, DataError.Remote> {
        return api.joinThread(threadId)
    }
}