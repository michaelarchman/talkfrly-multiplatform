package com.talkfrly.multiplatform.data.simpleThread.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thread2ListRequestDto(
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
    @SerialName("role") val role: String,
)

@Serializable
data class Thread2ListResponseDto(
    @SerialName("threads") val threads: List<Thread2Dto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class Thread2CreateRequestDto(
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("description") val description: String? = null,
)

@Serializable
data class Thread2Dto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("slug") val slug: String,
    @SerialName("description") val description: String? = null,
    @SerialName("creator_id") val creatorId: String,
    @SerialName("member_count") val memberCount: Int,
//    @SerialName("daily_post_count") val dailyPostCount: Int,
//    @SerialName("daily_visitors") val dailyVisitors: Int,
//    @SerialName("resolved_count") val resolvedCount: Int,
    @SerialName("is_member") val isMember: Boolean? = null,
    @SerialName("role") val role: String? = null,
    @SerialName("created_at") val createdAt: String,
    @SerialName("updated_at") val updatedAt: String,
)

@Serializable
data class Thread2UpdateRequestDto(
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
)

@Serializable
data class Thread2DeleteResponseDto(
    @SerialName("message") val message: String,
)
