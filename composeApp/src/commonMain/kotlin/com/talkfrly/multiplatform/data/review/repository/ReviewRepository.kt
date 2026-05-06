package com.talkfrly.multiplatform.data.review.repository

import com.talkfrly.multiplatform.data.review.api.ReviewApi
import com.talkfrly.multiplatform.data.review.mapper.toDomain
import com.talkfrly.multiplatform.data.review.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.review.CreateReviewRequest
import com.talkfrly.multiplatform.domain.review.Review
import com.talkfrly.multiplatform.domain.review.ReviewList
import com.talkfrly.multiplatform.domain.review.UpdateReviewRequest

interface ReviewRepository {
    suspend fun getCategories(): DataResult<List<String>, DataError.Remote>
    suspend fun getReviews(category: String, page: Int, limit: Int): DataResult<ReviewList, DataError.Remote>
    suspend fun getReviewById(id: String): DataResult<Review, DataError.Remote>
    suspend fun getReviewForPublication(publicationId: String): DataResult<Review, DataError.Remote>
    suspend fun createReview(publicationId: String, request: CreateReviewRequest): DataResult<Review, DataError.Remote>
    suspend fun updateReview(publicationId: String, request: UpdateReviewRequest): DataResult<Review, DataError.Remote>
}

class ReviewRepositoryImpl(
    private val api: ReviewApi,
) : ReviewRepository {
    override suspend fun getCategories(): DataResult<List<String>, DataError.Remote> {
        return api.getCategories().map { it.categories }
    }

    override suspend fun getReviews(
        category: String,
        page: Int,
        limit: Int,
    ): DataResult<ReviewList, DataError.Remote> {
        return api.getReviews(category, page, limit).map { it.toDomain() }
    }

    override suspend fun getReviewById(id: String): DataResult<Review, DataError.Remote> {
        return api.getReviewById(id).map { it.toDomain() }
    }

    override suspend fun getReviewForPublication(publicationId: String): DataResult<Review, DataError.Remote> {
        return api.getReviewForPublication(publicationId).map { it.toDomain() }
    }

    override suspend fun createReview(
        publicationId: String,
        request: CreateReviewRequest,
    ): DataResult<Review, DataError.Remote> {
        return api.createReview(publicationId, request.toDto()).map { it.toDomain() }
    }

    override suspend fun updateReview(
        publicationId: String,
        request: UpdateReviewRequest,
    ): DataResult<Review, DataError.Remote> {
        return api.updateReview(publicationId, request.toDto()).map { it.toDomain() }
    }
}