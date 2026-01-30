package com.talkfrly.multiplatform.ui.screens.publication

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PublicationDetailsViewModel(
    private val publicationId: String,
    private val publicationRepository: PublicationRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(PublicationDetailsState())
    val state: StateFlow<PublicationDetailsState> get() = _state

    fun onIntent(intent: PublicationDetailsIntent) {
        when (intent) {
            is PublicationDetailsIntent.GetPublicationDetails -> getPublicationDetails()
            is PublicationDetailsIntent.NavigateBack -> { }
        }
    }

    private fun getPublicationDetails() = viewModelScope.launch {
        startLoading()
        publicationRepository.getPublicationById(publicationId)
            .onSuccess { publication ->
                _state.update { it.copy(publication = publication, errorMessage = null) }
            }
            .onError { error ->
                _state.update { it.copy(errorMessage = "Failed to load publication") }
            }
            .onFinally {
                stopLoading()
            }
    }
}