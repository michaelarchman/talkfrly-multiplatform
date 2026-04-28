package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.data.uploads.ImageUploadStatus
import com.talkfrly.multiplatform.domain.comment.Comment
import com.talkfrly.multiplatform.domain.publication.Publication
import com.talkfrly.multiplatform.domain.user.User

data class PublicationScreenState(
    val isLoadingComments: Boolean = false,
    val isPostingComment: Boolean = false,
    val currentUser: User? = null,
    val publication: Publication? = null,
    val comments: List<Comment>? = null,
    val newCommentContent: String = "",
    val imageUri: String? = null,
    val imageUploadStatus: ImageUploadStatus? = null,
    val imageUploadError: String? = null,
    val uploadedImageUrl: String? = null,
)