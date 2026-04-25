package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.user.User

data class PublicationScreenState(
    val currentUser: User? = null,
    val publication: Publication? = null,
    val comments: List<Comment>? = null,
)