package com.talkfrly.multiplatform.data.feed.mapper

import com.talkfrly.multiplatform.data.feed.dto.FeedItemResponseDto
import com.talkfrly.multiplatform.data.feed.dto.FeedResponseDto
import com.talkfrly.multiplatform.data.feed.dto.FeedUserDto
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
    user = user.toDomain(),
//    domain = domain,
//    channelId = channelId,
    threadId = threadId,
//    threadSlug = threadSlug,
//    threadName = threadName,
//    publicationType = publicationType,
//    articleCategory = articleCategory,
    content = content,
    isAnonymous = isAnonymous,
    isPrivate = isPrivate,
    threadMembersOnly = threadMembersOnly,
//    isThreadMember = isThreadMember,
    imageUrls = imageUrls,
    tags = tags,
//    languages = languages,
//    videoId = videoId,
//    videoUrl = videoUrl,
//    videoThumbnail = videoThumbnail,
//    videoEmbedUrl = videoEmbedUrl,
//    videoStreamUid = videoStreamUid,
    commentCount = commentCount,
    voteScore = voteScore,
    likedByUser = likedByUser,
    views = views,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
