package com.talkfrly.multiplatform.data.threads.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreadListRequestDto(
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("role") val role: String,
)

@Serializable
data class ThreadListResponseDto(
    @SerialName("threads") val threads: List<ThreadDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class ThreadCreateRequestDto(
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("description") val description: String? = null,
)

@Serializable
data class ThreadDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("description") val description: String? = null,
    @SerialName("creator_id") val creatorId: String,
    @SerialName("role") val role: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class UpdateThreadRequestDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
)
