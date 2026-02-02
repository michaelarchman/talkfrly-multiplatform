package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.Comment

sealed class PublicationDetailsIntent {
    data object GetPublicationDetails : PublicationDetailsIntent()
    data object NavigateBack : PublicationDetailsIntent()
    data object GetComments : PublicationDetailsIntent()

    // Comment form
    data class UpdateCommentFormContent(val content: String) : PublicationDetailsIntent()
    data class UpdateCommentFormIsAnonymous(val isAnonymous: Boolean) : PublicationDetailsIntent()
    data object SubmitComment : PublicationDetailsIntent()

    // Reply form
    data class StartReply(val comment: Comment) : PublicationDetailsIntent()
    data object CancelReply : PublicationDetailsIntent()
    data class UpdateReplyFormContent(val content: String) : PublicationDetailsIntent()
    data class UpdateReplyFormIsAnonymous(val isAnonymous: Boolean) : PublicationDetailsIntent()
    data object SubmitReply : PublicationDetailsIntent()

    // Thread join
    data object JoinThread : PublicationDetailsIntent()

    // Menu actions
    data object ToggleMenu : PublicationDetailsIntent()
    data object EditPost : PublicationDetailsIntent()
    data object DeletePost : PublicationDetailsIntent()
    data object ReportPost : PublicationDetailsIntent()
}