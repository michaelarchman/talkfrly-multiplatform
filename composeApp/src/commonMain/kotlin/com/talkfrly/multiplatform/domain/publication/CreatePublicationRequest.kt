package com.talkfrly.multiplatform.domain.publication

data class CreatePublicationRequest(
    val content: String,
    val type: String? = null,
    val threadId: String? = null,
    val isAnonymous: Boolean = false,
    val isPrivate: Boolean = false,
    val threadMembersOnly: Boolean = false,
    val courseMembersOnly: Boolean = false,
    val courseId: String? = null,
    val lessonId: String? = null,
    val imageUrls: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val videoId: String? = null,
    val videoStreamUid: String? = null,
)