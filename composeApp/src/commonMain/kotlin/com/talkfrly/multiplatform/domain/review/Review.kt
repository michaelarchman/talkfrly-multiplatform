package com.talkfrly.multiplatform.domain.review

data class Review(
    val id: String,
    val publicationId: String,
    val subject: String,
    val rating: Int,
    val category: String,
    val pros: List<String>? = null,
    val cons: List<String>? = null,
    val createdAt: String,
    val updatedAt: String,
)

data class ReviewList(
    val reviews: List<Review>,
    val totalCount: Int,
    val page: Int,
    val limit: Int,
)

data class CreateReviewRequest(
    val subject: String,
    val rating: Int,
    val category: String,
    val pros: List<String>? = null,
    val cons: List<String>? = null,
)

data class UpdateReviewRequest(
    val subject: String,
    val rating: Int,
    val category: String,
    val pros: List<String>? = null,
    val cons: List<String>? = null,
)