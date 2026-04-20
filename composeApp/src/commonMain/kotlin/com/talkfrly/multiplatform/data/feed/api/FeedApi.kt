package com.talkfrly.multiplatform.data.feed.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.feed.dto.FeedResponseDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface FeedApi{
    suspend fun getFeed(page: Int, limit: Int): DataResult<FeedResponseDto, DataError.Remote>
    suspend fun getPopularFeed(page: Int, limit: Int): DataResult<FeedResponseDto, DataError.Remote>
}

class FeedApiImpl(
    private val httpClient: HttpClient
): FeedApi{
    override suspend fun getFeed(
        page: Int,
        limit: Int
    ): DataResult<FeedResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/feed",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf(
                "page" to page,
                "limit" to limit,
            )
        )
    }

    override suspend fun getPopularFeed(
        page: Int,
        limit: Int
    ): DataResult<FeedResponseDto, DataError.Remote> {
        return makeRequest(
            httpClient = httpClient,
            urlString = "/feed/popular",
            httpMethod = HttpMethod.Get,
            queryParams = mapOf(
                "page" to page,
                "limit" to limit,
            )
        )
    }

}
