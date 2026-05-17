package com.talkfrly.multiplatform.data.ranking.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RankedItemResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("score") val score: Int,
    @SerialName("user_vote") val userVote: Int? = null,
)

@Serializable
data class RankingResponseDto(
    @SerialName("id") val id: String,
    @SerialName("publication_id") val publicationId: String,
    @SerialName("items") val items: List<RankedItemResponseDto>,
    @SerialName("no_negative_scores") val noNegativeScores: Boolean,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class RankedItemRequestDto(
    @SerialName("id") val id: String? = null,
    @SerialName("name") val name: String,
)

@Serializable
data class CreateRankingRequestDto(
    @SerialName("items") val items: List<RankedItemRequestDto>,
    @SerialName("no_negative_scores") val noNegativeScores: Boolean? = null,
)

@Serializable
data class UpdateRankingRequestDto(
    @SerialName("items") val items: List<RankedItemRequestDto>,
    @SerialName("no_negative_scores") val noNegativeScores: Boolean? = null,
)

@Serializable
data class VoteRequestDto(
    @SerialName("value") val value: Int,
)