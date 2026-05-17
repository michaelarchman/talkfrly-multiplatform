package com.talkfrly.multiplatform.data.publications.mapper

import com.talkfrly.multiplatform.data.publications.dto.PublicationDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationFilterDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationListResponseDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationRequestDto
import com.talkfrly.multiplatform.data.publications.dto.UserSummaryDto
import com.talkfrly.multiplatform.data.ranking.mapper.toDomain
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.domain.feed.FeedUser
import com.talkfrly.multiplatform.domain.publication.CreatePublicationRequest
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.publication.PublicationFilter
import com.talkfrly.multiplatform.domain.publication.PublicationList
import com.talkfrly.multiplatform.domain.publication.UserSummary

fun PublicationDto.toDomain(): Publication = Publication(
    id = id,
    user = user?.toDomain(),
    channelId = channelId,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
    type = type,
    ranking = ranking?.toDomain(),
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
    tags = tags,
    languages = languages,
    avatarUrl = avatarUrl,
    threadMembersOnly = threadMembersOnly,
    courseMembersOnly = courseMembersOnly,
    courseId = courseId,
    lessonId = lessonId,
    lessonTitle = lessonTitle,
    moduleTitle = moduleTitle,
    isThreadMember = isThreadMember,
    commentCount = commentCount,
    voteScore = voteScore,
    likedByUser = likedByUser,
    likeCount = likeCount,
    views = views,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun UserSummaryDto.toDomain(): UserSummary = UserSummary(
    id = id,
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun PublicationListResponseDto.toDomain(): PublicationList = PublicationList(
    publications = publications.map { it.toDomain() },
    totalCount = totalCount,
    page = page,
    limit = limit,
)

fun PublicationFilter.toDto(): PublicationFilterDto = PublicationFilterDto(
    channelId = channelId,
    threadId = threadId,
    type = type,
    sort = sort,
    private = private,
)

fun CreatePublicationRequest.toDto(): PublicationRequestDto = PublicationRequestDto(
    content = content,
    type = type,
    threadId = threadId,
    isAnonymous = isAnonymous,
    isPrivate = isPrivate,
    threadMembersOnly = threadMembersOnly,
    courseMembersOnly = courseMembersOnly.takeIf { it },
    courseId = courseId,
    lessonId = lessonId,
    imageUrls = imageUrls.takeIf { it.isNotEmpty() },
    tags = tags.takeIf { it.isNotEmpty() },
    videoId = videoId,
    videoStreamUid = videoStreamUid,
)