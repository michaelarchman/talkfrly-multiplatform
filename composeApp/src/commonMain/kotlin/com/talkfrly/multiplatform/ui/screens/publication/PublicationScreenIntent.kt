package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.CreateCommentRequest

sealed class PublicationScreenIntent {
    data object GetCurrentUser : PublicationScreenIntent()
    data class GetPublications(val publicationId: String) : PublicationScreenIntent()
    data class DeletePublication(val id: String) : PublicationScreenIntent()
    data class GetComments(val publicationId: String) : PublicationScreenIntent()
    data class SetNewCommentContent(val content: String) : PublicationScreenIntent()
    data class AddImage(val uri: String) : PublicationScreenIntent()
    data object RemoveImage : PublicationScreenIntent()
    data class RetryImage(val uri: String) : PublicationScreenIntent()
    data class PostComment(val createCommentRequest: CreateCommentRequest) : PublicationScreenIntent()
    data class LikePublication(val publicationId: String) : PublicationScreenIntent()
    data class UnlikePublication(val publicationId: String) : PublicationScreenIntent()
    data class VoteRankingItem(val publicationId: String, val itemId: String, val value: Int) : PublicationScreenIntent()
    data class UpdateComment(val commentId: String, val content: CreateCommentRequest) : PublicationScreenIntent()
    data class DeleteComment(val publicationId: String, val commentId: String) : PublicationScreenIntent()
}
