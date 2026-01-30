package com.talkfrly.multiplatform.ui.screens.publication

sealed class PublicationDetailsIntent {
    data object GetPublicationDetails : PublicationDetailsIntent()
    data object NavigateBack : PublicationDetailsIntent()
}