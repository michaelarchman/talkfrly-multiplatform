package com.talkfrly.multiplatform.data.stream.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamListRequestDto(
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)

@Serializable
data class StreamListResponseDto(
    @SerialName("items") val items: List<StreamViewerResponseDto>,
    @SerialName("total_count") val totalCount: Int,
    @SerialName("page") val page: Int,
    @SerialName("limit") val limit: Int,
)
