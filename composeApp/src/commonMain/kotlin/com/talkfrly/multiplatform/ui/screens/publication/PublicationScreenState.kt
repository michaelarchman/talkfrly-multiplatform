package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.publication.Publication

data class PublicationScreenState(
    val publication: Publication? = null,
    val comments: List<Comment>? = null,
)