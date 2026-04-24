package com.talkfrly.multiplatform.ui.screens.publication

sealed class PublicationScreenIntent {
    data object GetPublications : PublicationScreenIntent()
    data object GetComments : PublicationScreenIntent()
}