package com.talkfrly.multiplatform.data.stream.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StreamKeyDto(
    @SerialName("rtmp_url") val rtmpUrl: String? = null,
    @SerialName("stream_key") val streamKey: String? = null,
    @SerialName("playback_url") val playbackUrl: String? = null
)