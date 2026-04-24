package com.talkfrly.multiplatform.ui.screens.publication

sealed class PublicationScreenIntent {
    data class GetPublications(val publicationId: String) : PublicationScreenIntent()
    data class GetComments(val publicationId: String) : PublicationScreenIntent()
}