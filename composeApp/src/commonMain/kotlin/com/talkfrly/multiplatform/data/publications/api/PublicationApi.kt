package com.talkfrly.multiplatform.data.publications.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.publications.dto.PublicationDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationFilterDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationListResponseDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface PublicationApi {
    suspend fun getPublications(page: Int, limit: Int, filter: PublicationFilterDto?): DataResult<PublicationListResponseDto, DataError.Remote>
    suspend fun getPublicationById(id: String): DataResult<PublicationDto, DataError.Remote>
    suspend fun createPublication(request: PublicationRequestDto): DataResult<PublicationDto, DataError.Remote>
    suspend fun updatePublication(id: String, request: PublicationRequestDto): DataResult<PublicationDto, DataError.Remote>
    suspend fun deletePublication(id: String): DataResult<Unit, DataError.Remote>
    suspend fun likePublication(id: String): DataResult<Unit, DataError.Remote>
    suspend fun unlikePublication(id: String): DataResult<Unit, DataError.Remote>
}

class PublicationApiImpl(
    private val httpClient: HttpClient,
) : PublicationApi {
    override suspend fun getPublications(
        page: Int,
        limit: Int,
        filter: PublicationFilterDto?,
    ): DataResult<PublicationListResponseDto, DataError.Remote> {
        val params = mutableMapOf<String, Any>(
            "page" to page,
            "limit" to limit,
        )
        filter?.channelId?.let { params["channel_id"] = it }
        filter?.threadId?.let { params["thread_id"] = it }
        filter?.type?.let { params["type"] = it }
        filter?.sort?.let { params["sort"] = it }
        filter?.private?.let { params["private"] = it }
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            queryParams = params,
            urlString = "/publications",
        )
    }

    override suspend fun getPublicationById(id: String): DataResult<PublicationDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/publications/$id",
        )
    }

    override suspend fun createPublication(request: PublicationRequestDto): DataResult<PublicationDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications",
            body = request,
        )
    }

    override suspend fun updatePublication(
        id: String,
        request: PublicationRequestDto,
    ): DataResult<PublicationDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Put,
            httpClient = httpClient,
            urlString = "/publications/$id",
            body = request,
        )
    }

    override suspend fun deletePublication(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Delete,
            httpClient = httpClient,
            urlString = "/publications/$id",
        )
    }

    override suspend fun likePublication(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$id/like",
        )
    }

    override suspend fun unlikePublication(id: String): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Delete,
            httpClient = httpClient,
            urlString = "/publications/$id/like",
        )
    }
}