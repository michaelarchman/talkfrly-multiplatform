package com.talkfrly.multiplatform.data.feed.mapper

import com.talkfrly.multiplatform.data.feed.dto.FeedItemResponseDto
import com.talkfrly.multiplatform.data.feed.dto.FeedResponseDto
import com.talkfrly.multiplatform.data.feed.dto.FeedUserDto
import com.talkfrly.multiplatform.data.ranking.mapper.toDomain
import com.talkfrly.multiplatform.domain.feed.Feed
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.domain.feed.FeedUser

fun FeedResponseDto.toDomain(): Feed = Feed(
    publications = feed.map { it.toDomain() },
    page = page,
    limit = limit,
)

fun FeedUserDto.toDomain(): FeedUser = FeedUser(
    id = id,
    displayName = displayName,
    avatarUrl = avatarUrl,
)

fun FeedItemResponseDto.toDomain(): FeedItem = FeedItem(
    id = id,
    user = user?.toDomain(),
    content = content,
    publicationType = publicationType,
    ranking = ranking?.toDomain(),
    articleCategory = articleCategory,
    isAnonymous = isAnonymous,
    isPrivate = isPrivate,
    threadMembersOnly = threadMembersOnly,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
    imageUrls = imageUrls,
    tags = tags,
    videoId = videoId,
    videoStreamUid = videoStreamUid,
    videoUrl = videoUrl,
    videoThumbnail = videoThumbnail,
    videoEmbedUrl = videoEmbedUrl,
    commentCount = commentCount,
    voteScore = voteScore,
    likedByUser = likedByUser,
    likeCount = likeCount,
    views = views,
    createdAt = createdAt,
    updatedAt = updatedAt,
)