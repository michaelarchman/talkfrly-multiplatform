package com.talkfrly.multiplatform.data.search.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PublicationSearchResultDto(
    @SerialName("id") val id: String,
    @SerialName("display_name") val displayName: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("type") val type: String,
    @SerialName("title") val title: String,
    @SerialName("tags") val tags: List<String>,
    @SerialName("created_at") val createdAt: String,
)

@Serializable
data class SearchResponseDto(
    @SerialName("publications") val publications: List<PublicationSearchResultDto>,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)