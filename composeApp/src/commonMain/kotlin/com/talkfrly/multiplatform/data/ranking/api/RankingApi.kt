package com.talkfrly.multiplatform.data.ranking.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.ranking.dto.CreateRankingRequestDto
import com.talkfrly.multiplatform.data.ranking.dto.RankingResponseDto
import com.talkfrly.multiplatform.data.ranking.dto.UpdateRankingRequestDto
import com.talkfrly.multiplatform.data.ranking.dto.VoteRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface RankingApi {
    suspend fun getRankingById(id: String): DataResult<RankingResponseDto, DataError.Remote>
    suspend fun getRankingForPublication(publicationId: String): DataResult<RankingResponseDto, DataError.Remote>
    suspend fun createRanking(publicationId: String, request: CreateRankingRequestDto): DataResult<RankingResponseDto, DataError.Remote>
    suspend fun updateRanking(publicationId: String, request: UpdateRankingRequestDto): DataResult<RankingResponseDto, DataError.Remote>
    suspend fun vote(publicationId: String, itemId: String, request: VoteRequestDto): DataResult<Unit, DataError.Remote>
    suspend fun removeVote(publicationId: String, itemId: String): DataResult<Unit, DataError.Remote>
}

class RankingApiImpl(
    private val httpClient: HttpClient,
) : RankingApi {
    override suspend fun getRankingById(id: String): DataResult<RankingResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/rankings/$id",
        )
    }

    override suspend fun getRankingForPublication(publicationId: String): DataResult<RankingResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/ranking",
        )
    }

    override suspend fun createRanking(
        publicationId: String,
        request: CreateRankingRequestDto,
    ): DataResult<RankingResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/ranking",
            body = request,
        )
    }

    override suspend fun updateRanking(
        publicationId: String,
        request: UpdateRankingRequestDto,
    ): DataResult<RankingResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Put,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/ranking",
            body = request,
        )
    }

    override suspend fun vote(
        publicationId: String,
        itemId: String,
        request: VoteRequestDto,
    ): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/ranking/items/$itemId/vote",
            body = request,
        )
    }

    override suspend fun removeVote(
        publicationId: String,
        itemId: String,
    ): DataResult<Unit, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Delete,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/ranking/items/$itemId/vote",
        )
    }
}