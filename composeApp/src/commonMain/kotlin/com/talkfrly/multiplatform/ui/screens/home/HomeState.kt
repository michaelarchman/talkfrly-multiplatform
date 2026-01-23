package com.talkfrly.multiplatform.ui.screens.home

import com.talkfrly.multiplatform.domain.publication.Publication

data class HomeState(
    val message: String = "Welcome to Home!",
    val publications: List<Publication> = emptyList()
)