package com.talkfrly.multiplatform.data.ranking.repository

import com.talkfrly.multiplatform.data.ranking.api.RankingApi
import com.talkfrly.multiplatform.data.ranking.mapper.toDomain
import com.talkfrly.multiplatform.data.ranking.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.ranking.CreateRankingRequest
import com.talkfrly.multiplatform.domain.ranking.Ranking
import com.talkfrly.multiplatform.domain.ranking.UpdateRankingRequest

interface RankingRepository {
    suspend fun getRankingById(id: String): DataResult<Ranking, DataError.Remote>
    suspend fun getRankingForPublication(publicationId: String): DataResult<Ranking, DataError.Remote>
    suspend fun createRanking(publicationId: String, request: CreateRankingRequest): DataResult<Ranking, DataError.Remote>
    suspend fun updateRanking(publicationId: String, request: UpdateRankingRequest): DataResult<Ranking, DataError.Remote>
    suspend fun vote(publicationId: String, itemId: String, value: Int): DataResult<Unit, DataError.Remote>
    suspend fun removeVote(publicationId: String, itemId: String): DataResult<Unit, DataError.Remote>
}

class RankingRepositoryImpl(
    private val api: RankingApi,
) : RankingRepository {
    override suspend fun getRankingById(id: String): DataResult<Ranking, DataError.Remote> {
        return api.getRankingById(id).map { it.toDomain() }
    }

    override suspend fun getRankingForPublication(publicationId: String): DataResult<Ranking, DataError.Remote> {
        return api.getRankingForPublication(publicationId).map { it.toDomain() }
    }

    override suspend fun createRanking(
        publicationId: String,
        request: CreateRankingRequest,
    ): DataResult<Ranking, DataError.Remote> {
        return api.createRanking(publicationId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun updateRanking(
        publicationId: String,
        request: UpdateRankingRequest,
    ): DataResult<Ranking, DataError.Remote> {
        return api.updateRanking(publicationId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun vote(
        publicationId: String,
        itemId: String,
        value: Int,
    ): DataResult<Unit, DataError.Remote> {
        return api.vote(publicationId, itemId, value.toDto())
    }

    override suspend fun removeVote(
        publicationId: String,
        itemId: String,
    ): DataResult<Unit, DataError.Remote> {
        return api.removeVote(publicationId, itemId)
    }
}