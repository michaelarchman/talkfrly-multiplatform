package com.talkfrly.multiplatform.domain.publication

data class CreatePublicationRequest(
    val title: String,
    val content: String,
    val publicationType: PublicationType,
    val threadId: String? = null,
    val isAnonymous: Boolean = false,
)