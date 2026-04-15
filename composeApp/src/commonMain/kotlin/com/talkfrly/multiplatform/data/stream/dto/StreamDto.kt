package com.talkfrly.multiplatform.data.stream.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("is_live") val isLive: Boolean,
    @SerialName("playback_url") val playbackUrl: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String
)

@Serializable
data class StreamViewerResponseDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("owner_id") val ownerId: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("is_live") val isLive: Boolean,
    @SerialName("playback_url") val playbackUrl: String? = null,
)

@Serializable
data class StreamRequestDto(
    @SerialName("name") val name: String,
    @SerialName("category") val category: String,
    @SerialName("avatar_url") val avatarUrl: String? = null
)

@Serializable
data class StreamStopResponseDto(
    @SerialName("message") val message: String
)