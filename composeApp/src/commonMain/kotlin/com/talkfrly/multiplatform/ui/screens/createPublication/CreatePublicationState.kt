package com.talkfrly.multiplatform.ui.screens.createPublication

import com.talkfrly.multiplatform.data.uploads.ImageUploadStatus
import com.talkfrly.multiplatform.domain.publication.PublicationType

enum class VisibilityOption {
    PUBLIC,
    THREAD_ONLY,
    PRIVATE
}

data class CreatePublicationState(
    val selectedType: PublicationType = PublicationType.GENERAL,
    val title: String = "",
    val content: String = "",
    val isAnonymous: Boolean = false,
    val visibility: VisibilityOption = VisibilityOption.PUBLIC,
    val imageUris: List<String> = emptyList(),
    val imageUploadStatus: Map<String, ImageUploadStatus> = emptyMap(),
    val imageUploadErrors: Map<String, String> = emptyMap(),
    val uploadedImageUrls: Map<String, String> = emptyMap(),
    val tags: List<String> = emptyList(),
    val tagInput: String = "",
    val isSubmitting: Boolean = false,
    val isSubmitted: Boolean = false,
    val error: String? = null,
    val inThreadContext: Boolean = false,
    val threadId: String? = null,
    val threadName: String? = null,
    val articleCategory: String? = null,
    val articleAvailableCategories: List<String>? = emptyList(),
    val articleSource: String = "",
    val articleAuthorName: String = "",
    val articleBibliographyInput: String = "",
)
