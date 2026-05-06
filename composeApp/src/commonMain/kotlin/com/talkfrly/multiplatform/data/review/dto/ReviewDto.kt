package com.talkfrly.multiplatform.data.review.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReviewResponseDto(
    @SerialName("id") val id: String,
    @SerialName("publication_id") val publicationId: String,
    @SerialName("subject") val subject: String,
    @SerialName("rating") val rating: Int,
    @SerialName("category") val category: String,
    @SerialName("pros") val pros: List<String>? = null,
    @SerialName("cons") val cons: List<String>? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class ReviewListResponseDto(
    @SerialName("reviews") val reviews: List<ReviewResponseDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class ReviewCategoriesResponseDto(
    @SerialName("categories") val categories: List<String>,
)

@Serializable
data class CreateReviewRequestDto(
    @SerialName("subject") val subject: String,
    @SerialName("rating") val rating: Int,
    @SerialName("category") val category: String,
    @SerialName("pros") val pros: List<String>? = null,
    @SerialName("cons") val cons: List<String>? = null,
)

@Serializable
data class UpdateReviewRequestDto(
    @SerialName("subject") val subject: String,
    @SerialName("rating") val rating: Int,
    @SerialName("category") val category: String,
    @SerialName("pros") val pros: List<String>? = null,
    @SerialName("cons") val cons: List<String>? = null,
)