package com.talkfrly.multiplatform.data.ranking.mapper

import com.talkfrly.multiplatform.data.ranking.dto.CreateRankingRequestDto
import com.talkfrly.multiplatform.data.ranking.dto.RankedItemRequestDto
import com.talkfrly.multiplatform.data.ranking.dto.RankedItemResponseDto
import com.talkfrly.multiplatform.data.ranking.dto.RankingResponseDto
import com.talkfrly.multiplatform.data.ranking.dto.UpdateRankingRequestDto
import com.talkfrly.multiplatform.data.ranking.dto.VoteRequestDto
import com.talkfrly.multiplatform.domain.ranking.CreateRankingRequest
import com.talkfrly.multiplatform.domain.ranking.RankedItem
import com.talkfrly.multiplatform.domain.ranking.RankedItemRequest
import com.talkfrly.multiplatform.domain.ranking.Ranking
import com.talkfrly.multiplatform.domain.ranking.UpdateRankingRequest

fun RankingResponseDto.toDomain(): Ranking = Ranking(
    id = id,
    publicationId = publicationId,
    items = items.map { it.toDomain() },
    noNegativeScores = noNegativeScores,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun RankedItemResponseDto.toDomain(): RankedItem = RankedItem(
    id = id,
    name = name,
    score = score,
    userVote = userVote,
)

fun CreateRankingRequest.toDto(): CreateRankingRequestDto = CreateRankingRequestDto(
    items = items.map { it.toDto() },
    noNegativeScores = noNegativeScores,
)

fun UpdateRankingRequest.toDto(): UpdateRankingRequestDto = UpdateRankingRequestDto(
    items = items.map { it.toDto() },
    noNegativeScores = noNegativeScores,
)

fun RankedItemRequest.toDto(): RankedItemRequestDto = RankedItemRequestDto(
    id = id,
    name = name,
)

fun Int.toDto(): VoteRequestDto = VoteRequestDto(value = this)