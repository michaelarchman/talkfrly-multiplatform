package com.talkfrly.multiplatform.ui.screens.createPublication

import com.talkfrly.multiplatform.domain.publication.PublicationType

sealed class CreatePublicationIntent {
    data class SetType(val type: PublicationType) : CreatePublicationIntent()
    data class SetTitle(val title: String) : CreatePublicationIntent()
    data class SetContent(val content: String) : CreatePublicationIntent()
    data class SetAnonymous(val isAnonymous: Boolean) : CreatePublicationIntent()
    data class SetVisibility(val visibility: VisibilityOption) : CreatePublicationIntent()
    data object OpenCamera : CreatePublicationIntent()
    data object OpenGallery : CreatePublicationIntent()
    data class AddImages(val uris: List<String>) : CreatePublicationIntent()
    data class RemoveImage(val uri: String) : CreatePublicationIntent()
    data class RetryImage(val uri: String) : CreatePublicationIntent()
    data class SetTagInput(val input: String) : CreatePublicationIntent()
    data class AddTag(val tag: String) : CreatePublicationIntent()
    data class RemoveTag(val tag: String) : CreatePublicationIntent()
    data object Submit : CreatePublicationIntent()
    data object NavigateBack : CreatePublicationIntent()
}
