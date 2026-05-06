package com.talkfrly.multiplatform.data.stream.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.stream.dto.StreamCategoryListResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamDashboardResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamKeyDto
import com.talkfrly.multiplatform.data.stream.dto.StreamListRequestDto
import com.talkfrly.multiplatform.data.stream.dto.StreamListResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamRequestDto
import com.talkfrly.multiplatform.data.stream.dto.StreamStopResponseDto
import com.talkfrly.multiplatform.data.stream.dto.StreamViewerResponseDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface StreamApi {
    suspend fun streamList(
        streamListRequestDto: StreamListRequestDto,
    ): DataResult<StreamListResponseDto, DataError.Remote>

    suspend fun getCategories(page: Int = 1, limit: Int = 50): DataResult<StreamCategoryListResponseDto, DataError.Remote>

    suspend fun createStream(
        streamRequestDto: StreamRequestDto,
    ): DataResult<StreamDashboardResponseDto, DataError.Remote>

    suspend fun getCurrentStream(): DataResult<StreamDashboardResponseDto, DataError.Remote>
    suspend fun getStreamById(id: String): DataResult<StreamViewerResponseDto, DataError.Remote>

    suspend fun updateStream(
        id: String,
        streamRequestDto: StreamRequestDto,
    ): DataResult<StreamDashboardResponseDto, DataError.Remote>

    suspend fun deleteStream(id: String): DataResult<Unit, DataError.Remote>
    suspend fun setupLiveInput(id: String): DataResult<StreamKeyDto, DataError.Remote>
    suspend fun stop(id: String): DataResult<StreamStopResponseDto, DataError.Remote>
    suspend fun getStreamKey(id: String): DataResult<StreamKeyDto, DataError.Remote>
}

class StreamApiImpl(
    private val httpClient: HttpClient,
) : StreamApi {
    override suspend fun streamList(
        streamListRequestDto: StreamListRequestDto,
    ): DataResult<StreamListResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf(
                "page" to streamListRequestDto.page,
                "limit" to streamListRequestDto.limit,
            ),
        )
    }

    override suspend fun getCategories(page: Int, limit: Int): DataResult<StreamCategoryListResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/categories",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf("page" to page, "limit" to limit),
        )
    }

    override suspend fun createStream(
        streamRequestDto: StreamRequestDto,
    ): DataResult<StreamDashboardResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams",
            httpMethod = HttpMethod.Post,
            body = streamRequestDto,
        )
    }

    override suspend fun getCurrentStream(): DataResult<StreamDashboardResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/me",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun getStreamById(id: String): DataResult<StreamViewerResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id",
            httpMethod = HttpMethod.Get,
        )
    }

    override suspend fun updateStream(
        id: String,
        streamRequestDto: StreamRequestDto,
    ): DataResult<StreamDashboardResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id",
            httpMethod = HttpMethod.Put,
            body = streamRequestDto,
        )
    }

    override suspend fun deleteStream(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id",
            httpMethod = HttpMethod.Delete,
        )
    }

    override suspend fun setupLiveInput(id: String): DataResult<StreamKeyDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id/live-input",
            httpMethod = HttpMethod.Post,
        )
    }

    override suspend fun stop(id: String): DataResult<StreamStopResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id/stop",
            httpMethod = HttpMethod.Post,
        )
    }

    override suspend fun getStreamKey(id: String): DataResult<StreamKeyDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/streams/$id/stream-key",
            httpMethod = HttpMethod.Get,
        )
    }
}