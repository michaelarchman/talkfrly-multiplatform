package com.talkfrly.multiplatform.data.search.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.search.dto.SearchResponseDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface SearchApi {
    suspend fun search(query: String, page: Int, limit: Int): DataResult<SearchResponseDto, DataError.Remote>
}

class SearchApiImpl(
    private val httpClient: HttpClient,
) : SearchApi {
    override suspend fun search(
        query: String,
        page: Int,
        limit: Int,
    ): DataResult<SearchResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/search",
            queryParams = mapOf(
                "q" to query,
                "page" to page,
                "limit" to limit,
            ),
        )
    }
}