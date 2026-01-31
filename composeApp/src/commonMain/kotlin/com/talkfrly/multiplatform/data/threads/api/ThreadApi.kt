package com.talkfrly.multiplatform.data.threads.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod
import kotlinx.serialization.Serializable

interface ThreadApi {
    suspend fun joinThread(threadId: String): DataResult<EmptyResponse, DataError.Remote>
}

@Serializable
data class EmptyResponse(val success: Boolean = true)

class ThreadApiImpl(
    private val httpClient: HttpClient,
) : ThreadApi {
    override suspend fun joinThread(threadId: String): DataResult<EmptyResponse, DataError.Remote> {
        return makeRequest<EmptyResponse>(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/threads/$threadId/join",
        )
    }
}