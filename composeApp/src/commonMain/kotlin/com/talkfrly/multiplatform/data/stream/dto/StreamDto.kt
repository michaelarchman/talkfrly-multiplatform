package com.talkfrly.multiplatform.data.stream.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamDashboardResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("is_live") val isLive: Boolean,
    @SerialName("playback_url") val playbackUrl: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class StreamViewerResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("display_name") val displayName: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("thumbnail_url") val thumbnailUrl: String? = null,
    @SerialName("is_live") val isLive: Boolean,
    @SerialName("playback_url") val playbackUrl: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
    @SerialName("thread_slug") val threadSlug: String? = null,
    @SerialName("thread_name") val threadName: String? = null,
)

@Serializable
data class StreamRequestDto(
    @SerialName("name") val name: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("thread_id") val threadId: String? = null,
)

@Serializable
data class StreamStopResponseDto(
    @SerialName("message") val message: String,
)

@Serializable
data class StreamCategoryResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("cover_url") val coverUrl: String? = null,
)

@Serializable
data class StreamCategoryListResponseDto(
    @SerialName("items") val items: List<StreamCategoryResponseDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)