package com.talkfrly.multiplatform.data.review.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.review.dto.CreateReviewRequestDto
import com.talkfrly.multiplatform.data.review.dto.ReviewCategoriesResponseDto
import com.talkfrly.multiplatform.data.review.dto.ReviewListResponseDto
import com.talkfrly.multiplatform.data.review.dto.ReviewResponseDto
import com.talkfrly.multiplatform.data.review.dto.UpdateReviewRequestDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface ReviewApi {
    suspend fun getCategories(): DataResult<ReviewCategoriesResponseDto, DataError.Remote>
    suspend fun getReviews(category: String, page: Int, limit: Int): DataResult<ReviewListResponseDto, DataError.Remote>
    suspend fun getReviewById(id: String): DataResult<ReviewResponseDto, DataError.Remote>
    suspend fun getReviewForPublication(publicationId: String): DataResult<ReviewResponseDto, DataError.Remote>
    suspend fun createReview(publicationId: String, request: CreateReviewRequestDto): DataResult<ReviewResponseDto, DataError.Remote>
    suspend fun updateReview(publicationId: String, request: UpdateReviewRequestDto): DataResult<ReviewResponseDto, DataError.Remote>
}

class ReviewApiImpl(
    private val httpClient: HttpClient,
) : ReviewApi {
    override suspend fun getCategories(): DataResult<ReviewCategoriesResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/reviews/categories",
        )
    }

    override suspend fun getReviews(
        category: String,
        page: Int,
        limit: Int,
    ): DataResult<ReviewListResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/reviews",
            queryParams = mapOf(
                "category" to category,
                "page" to page,
                "limit" to limit,
            ),
        )
    }

    override suspend fun getReviewById(id: String): DataResult<ReviewResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/reviews/$id",
        )
    }

    override suspend fun getReviewForPublication(publicationId: String): DataResult<ReviewResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/review",
        )
    }

    override suspend fun createReview(
        publicationId: String,
        request: CreateReviewRequestDto,
    ): DataResult<ReviewResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/review",
            body = request,
        )
    }

    override suspend fun updateReview(
        publicationId: String,
        request: UpdateReviewRequestDto,
    ): DataResult<ReviewResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Put,
            httpClient = httpClient,
            urlString = "/publications/$publicationId/review",
            body = request,
        )
    }
}