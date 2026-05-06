package com.talkfrly.multiplatform.domain.ranking

data class Ranking(
    val id: String,
    val publicationId: String,
    val items: List<RankedItem>,
    val noNegativeScores: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class RankedItem(
    val id: String,
    val name: String,
    val score: Int,
    val userVote: Int? = null,
)

data class RankedItemRequest(
    val id: String? = null,
    val name: String,
)

data class CreateRankingRequest(
    val items: List<RankedItemRequest>,
    val noNegativeScores: Boolean = false,
)

data class UpdateRankingRequest(
    val items: List<RankedItemRequest>,
    val noNegativeScores: Boolean = false,
)