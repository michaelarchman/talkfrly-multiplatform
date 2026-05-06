package com.talkfrly.multiplatform.data.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorDto(
    val status: Int? = null,
    val error: String? = null,
    val message: String? = null,
)

@Serializable
data class MessageDto(
    @SerialName("message") val message: String? = null,
)