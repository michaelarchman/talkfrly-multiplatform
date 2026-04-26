package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest

sealed class PublicationScreenIntent {
    data object GetCurrentUser : PublicationScreenIntent()
    data class GetPublications(val publicationId: String) : PublicationScreenIntent()
    data class GetComments(val publicationId: String) : PublicationScreenIntent()
    data class SetNewCommentContent(val content: String) : PublicationScreenIntent()
    data class PostComment(val createCommentRequest: CreateCommentRequest) : PublicationScreenIntent()
    data class LikePublication(val publicationId: String) : PublicationScreenIntent()
    data class UnlikePublication(val publicationId: String) : PublicationScreenIntent()
}