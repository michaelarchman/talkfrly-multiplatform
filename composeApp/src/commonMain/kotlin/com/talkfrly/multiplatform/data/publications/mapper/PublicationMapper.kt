package com.talkfrly.multiplatform.data.publications.mapper

import com.talkfrly.multiplatform.data.publications.dto.PublicationRequest
import com.talkfrly.multiplatform.data.publications.dto.CriterionSummaryDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationFilterDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationListResponseDto
import com.talkfrly.multiplatform.data.publications.dto.UserSummaryDto
import com.talkfrly.multiplatform.domain.publication.CreatePublicationRequest
import com.talkfrly.multiplatform.domain.publication.CriterionSummary
import com.talkfrly.multiplatform.domain.publication.ModuleType
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.publication.PublicationFilter
import com.talkfrly.multiplatform.domain.publication.PublicationList
import com.talkfrly.multiplatform.domain.publication.UserSummary

fun PublicationDto.toDomain(): Publication = Publication(
    id = id,
    userId = userId,
    user = user?.toDomain(),
    channelId = channelId,
    topicId = topicId,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
    moduleType = moduleType?.toDomainModuleType(),
    articleCategory = articleCategory,
    content = content,
    isAnonymous = isAnonymous,
    isPrivate = isPrivate,
    imageUrls = imageUrls,
    videoId = videoId,
    videoStreamUid = videoStreamUid,
    videoUrl = videoUrl,
    videoThumbnail = videoThumbnail,
    videoEmbedUrl = videoEmbedUrl,
    tags = tags ?: emptyList(),
    languages = languages ?: emptyList(),
    criteria = criteria?.map { it.toDomain() },
    pseudonym = pseudonym,
    avatarUrl = avatarUrl,
    threadMembersOnly = threadMembersOnly,
    isThreadMember = isThreadMember,
    commentCount = commentCount,
    voteScore = voteScore,
    likedByUser = likedByUser,
    views = views,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun UserSummaryDto.toDomain(): UserSummary = UserSummary(
    id = id,
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun CriterionSummaryDto.toDomain(): CriterionSummary = CriterionSummary(
    name = name,
    average = average,
    voteCount = voteCount,
    userScore = userScore,
)

fun String.toDomainModuleType(): ModuleType = when (this) {
    "general" -> ModuleType.GENERAL
    "articles" -> ModuleType.ARTICLES
    "rankings" -> ModuleType.RANKINGS
    "reviews" -> ModuleType.REVIEWS
    else -> ModuleType.UNKNOWN
}

fun PublicationListResponseDto.toDomain(): PublicationList = PublicationList(
    publications = publications.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun PublicationFilter.toDto(): PublicationFilterDto = PublicationFilterDto(
    channelId = channelId,
    topicId = topicId,
    threadId = threadId,
    moduleType = moduleType?.toDtoModuleType(),
)

fun ModuleType.toDtoModuleType(): String = when (this) {
    ModuleType.GENERAL -> "general"
    ModuleType.ARTICLES -> "articles"
    ModuleType.RANKINGS -> "rankings"
    ModuleType.REVIEWS -> "reviews"
    ModuleType.UNKNOWN -> "unknown"
}

fun CreatePublicationRequest.toDto(): PublicationRequest = PublicationRequest(
    title = title,
    content = content,
    publicationType = publicationType.name.uppercase(),
    threadId = threadId,
    isAnonymous = isAnonymous,
    imageUrls = imageUrls,
)
