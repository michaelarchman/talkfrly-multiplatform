package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.domain.publication.PublicationList

data class HomeState(
    val message: String = "Welcome to Home!",
    val publications: PublicationList? = null
)