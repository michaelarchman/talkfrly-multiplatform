package com.talkfrly.multiplatform.data.feed.mapper

import com.talkfrly.multiplatform.data.feed.dto.FeedItemResponseDto
import com.talkfrly.multiplatform.data.feed.dto.FeedResponseDto
import com.talkfrly.multiplatform.domain.feed.Feed
import com.talkfrly.multiplatform.domain.feed.FeedItem

fun FeedResponseDto.toDomain(): Feed = Feed(
    feed = feed.map { it.toDomain() },
    page = page,
    limit = limit,
)

fun FeedItemResponseDto.toDomain(): FeedItem = FeedItem(
    id = id,
    domain = domain,
    channelId = channelId,
    threadId = threadId,
    threadSlug = threadSlug,
    threadName = threadName,
    publicationType = publicationType,
    articleCategory = articleCategory,
    content = content,
    isAnonymous = isAnonymous,
    isPrivate = isPrivate,
    threadMembersOnly = threadMembersOnly,
    isThreadMember = isThreadMember,
    imageUrls = imageUrls,
    tags = tags,
    languages = languages,
    avatarUrl = avatarUrl,
    videoId = videoId,
    videoUrl = videoUrl,
    videoThumbnail = videoThumbnail,
    videoEmbedUrl = videoEmbedUrl,
    videoStreamUid = videoStreamUid,
    commentCount = commentCount,
    voteScore = voteScore,
    likedByUser = likedByUser,
    views = views,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
