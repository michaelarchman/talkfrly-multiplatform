package com.talkfrly.multiplatform.ui.screens.publication

sealed class PublicationScreenIntent {
    data object GetCurrentUser : PublicationScreenIntent()
    data class GetPublications(val publicationId: String) : PublicationScreenIntent()
    data class GetComments(val publicationId: String) : PublicationScreenIntent()
    data class LikePublication(val publicationId: String) : PublicationScreenIntent()
    data class UnlikePublication(val publicationId: String) : PublicationScreenIntent()
}