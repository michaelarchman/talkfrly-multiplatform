package com.talkfrly.multiplatform.data.review.mapper

import com.talkfrly.multiplatform.data.review.dto.CreateReviewRequestDto
import com.talkfrly.multiplatform.data.review.dto.ReviewListResponseDto
import com.talkfrly.multiplatform.data.review.dto.ReviewResponseDto
import com.talkfrly.multiplatform.data.review.dto.UpdateReviewRequestDto
import com.talkfrly.multiplatform.domain.review.CreateReviewRequest
import com.talkfrly.multiplatform.domain.review.Review
import com.talkfrly.multiplatform.domain.review.ReviewList
import com.talkfrly.multiplatform.domain.review.UpdateReviewRequest

fun ReviewResponseDto.toDomain(): Review = Review(
    id = id,
    publicationId = publicationId,
    subject = subject,
    rating = rating,
    category = category,
    pros = pros,
    cons = cons,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun ReviewListResponseDto.toDomain(): ReviewList = ReviewList(
    reviews = reviews.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun CreateReviewRequest.toDto(): CreateReviewRequestDto = CreateReviewRequestDto(
    subject = subject,
    rating = rating,
    category = category,
    pros = pros,
    cons = cons,
)

fun UpdateReviewRequest.toDto(): UpdateReviewRequestDto = UpdateReviewRequestDto(
    subject = subject,
    rating = rating,
    category = category,
    pros = pros,
    cons = cons,
)