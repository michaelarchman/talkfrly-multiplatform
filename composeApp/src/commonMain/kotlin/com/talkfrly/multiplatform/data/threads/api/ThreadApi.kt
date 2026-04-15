package com.talkfrly.multiplatform.data.threads.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.threads.dto.ThreadCreateRequestDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadListRequestDto
import com.talkfrly.multiplatform.data.threads.dto.ThreadListResponseDto
import com.talkfrly.multiplatform.data.threads.dto.UpdateThreadRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable

interface ThreadApi {
    suspend fun threadList(threadListRequestDto: ThreadListRequestDto): DataResult<ThreadListResponseDto, DataError.Remote>
    suspend fun createThread(threadCreateRequestDto: ThreadCreateRequestDto): DataResult<ThreadDto, DataError.Remote>
    suspend fun getThreadById(id: String): DataResult<ThreadDto, DataError.Remote>
    suspend fun updateThread(id: String, updateThreadRequestDto: UpdateThreadRequestDto): DataResult<ThreadDto, DataError.Remote>
    suspend fun deleteThread(id: String): DataResult<Unit, DataError.Remote>
    suspend fun getThreadBySlug(slug: String): DataResult<ThreadDto, DataError.Remote>
    suspend fun joinThread(id: String): DataResult<Unit, DataError.Remote>
    suspend fun leaveThread(id: String): DataResult<Unit, DataError.Remote>
}

@Serializable
data class EmptyResponse(val success: Boolean = true)

class ThreadApiImpl(
    private val httpClient: HttpClient,
) : ThreadApi {
    override suspend fun threadList(threadListRequestDto: ThreadListRequestDto): DataResult<ThreadListResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf(
                "page" to threadListRequestDto.page,
                "limit" to threadListRequestDto.limit,
                "role" to threadListRequestDto.role
            )
        )
    }

    override suspend fun createThread(threadCreateRequestDto: ThreadCreateRequestDto): DataResult<ThreadDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads",
            httpMethod = HttpMethod.Post,
            body = threadCreateRequestDto
        )
    }

    override suspend fun getThreadById(id: String): DataResult<ThreadDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun updateThread(id: String, updateThreadRequestDto: UpdateThreadRequestDto): DataResult<ThreadDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Put,
            body = updateThreadRequestDto
        )
    }

    override suspend fun deleteThread(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id",
            httpMethod = HttpMethod.Delete,
        )
    }

    override suspend fun getThreadBySlug(slug: String): DataResult<ThreadDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/slug/$slug",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun joinThread(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id/join",
            httpMethod = HttpMethod.Post,
        )
    }

    override suspend fun leaveThread(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/threads/$id/leave",
            httpMethod = HttpMethod.Delete,
        )
    }
}