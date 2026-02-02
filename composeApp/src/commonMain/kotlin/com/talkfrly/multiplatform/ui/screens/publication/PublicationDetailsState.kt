package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.publication.Publication

data class PublicationDetailsState(
    val publication: Publication? = null,
    val errorMessage: String? = null,

    // Comments
    val comments: List<Comment> = emptyList(),
    val isLoadingComments: Boolean = false,
    val commentsError: String? = null,

    // Comment form
    val commentFormContent: String = "",
    val commentFormIsAnonymous: Boolean = false,
    val isSubmittingComment: Boolean = false,

    // Reply form
    val replyingTo: Comment? = null,
    val replyFormContent: String = "",
    val replyFormIsAnonymous: Boolean = false,
    val isSubmittingReply: Boolean = false,

    // Thread join
    val isJoiningThread: Boolean = false,

    // Menu
    val isMenuExpanded: Boolean = false,
    val currentUserId: String? = null,
    val isAdmin: Boolean = false,
)