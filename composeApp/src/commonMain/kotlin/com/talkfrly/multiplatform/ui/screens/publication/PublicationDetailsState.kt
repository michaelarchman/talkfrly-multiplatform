package com.talkfrly.multiplatform.ui.screens.publication

import com.talkfrly.multiplatform.domain.publication.Publication

data class PublicationDetailsState(
    val publication: Publication? = null,
    val errorMessage: String? = null,
)