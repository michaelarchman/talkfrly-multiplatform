package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.Comment

sealed class PublicationScreenIntent {
    data object GetPublicationScreen : PublicationScreenIntent()
    data object NavigateBack : PublicationScreenIntent()
    data object GetComments : PublicationScreenIntent()

    // Comment form
    data class UpdateCommentFormContent(val content: String) : PublicationScreenIntent()
    data class UpdateCommentFormIsAnonymous(val isAnonymous: Boolean) : PublicationScreenIntent()
    data object SubmitComment : PublicationScreenIntent()

    // Reply form
    data class StartReply(val comment: Comment) : PublicationScreenIntent()
    data object CancelReply : PublicationScreenIntent()
    data class UpdateReplyFormContent(val content: String) : PublicationScreenIntent()
    data class UpdateReplyFormIsAnonymous(val isAnonymous: Boolean) : PublicationScreenIntent()
    data object SubmitReply : PublicationScreenIntent()

    // Thread join
    data object JoinThread : PublicationScreenIntent()

    // Menu actions
    data object ToggleMenu : PublicationScreenIntent()
    data object EditPost : PublicationScreenIntent()
    data object DeletePost : PublicationScreenIntent()
    data object ReportPost : PublicationScreenIntent()
}