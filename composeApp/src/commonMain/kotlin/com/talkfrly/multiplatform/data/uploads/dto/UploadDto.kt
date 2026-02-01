package com.talkfrly.multiplatform.data.uploads.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PresignUploadRequestDto(
    @SerialName("filename") val filename: String
)

@Serializable
data class PresignUploadResponseDto(
    @SerialName("upload_url") val uploadUrl: String,
    @SerialName("file_url") val fileUrl: String,
    @SerialName("key") val key: String
)
