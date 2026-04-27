package com.talkfrly.multiplatform.data.simpleThread.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2CreateRequestDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2DeleteResponseDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2Dto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2ListRequestDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2ListResponseDto
import com.talkfrly.multiplatform.data.simpleThread.dto.Thread2UpdateRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface Thread2Api {
    suspend fun getThreads(request: Thread2ListRequestDto): DataResult<Thread2ListResponseDto, DataError.Remote>
    suspend fun createThread(request: Thread2CreateRequestDto): DataResult<Thread2Dto, DataError.Remote>
    suspend fun getThreadById(id: String): DataResult<Thread2Dto, DataError.Remote>
    suspend fun updateThread(id: String, request: Thread2UpdateRequestDto): DataResult<Thread2Dto, DataError.Remote>
    suspend fun deleteThread(id: String): DataResult<Thread2DeleteResponseDto, DataError.Remote>
}

class Thread2ApiImpl(
    private val httpClient: HttpClient,
) : Thread2Api {
    override suspend fun getThreads(request: Thread2ListRequestDto): DataResult<Thread2ListResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf(
                "page" to request.page,
                "limit" to request.limit,
                "role" to request.role
            )
        )
    }

    override suspend fun createThread(request: Thread2CreateRequestDto): DataResult<Thread2Dto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads",
            httpMethod = HttpMethod.Post,
            body = request,
        )
    }

    override suspend fun getThreadById(id: String): DataResult<Thread2Dto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun updateThread(
        id: String,
        request: Thread2UpdateRequestDto
    ): DataResult<Thread2Dto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Put,
            body = request,
        )
    }

    override suspend fun deleteThread(id: String): DataResult<Thread2DeleteResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Delete,
        )
    }
}
