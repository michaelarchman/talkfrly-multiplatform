package com.talkfrly.multiplatform.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.publication.PublicationFilter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val publicationRepository: PublicationRepository
) : BaseViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.Logout -> { }
            is HomeIntent.GetPublications -> getPublications()
        }
    }

    private fun getPublications() = viewModelScope.launch {
        startLoading()
        publicationRepository.getPublications(
            page = 1,
            limit = 10,
            filter = PublicationFilter()
        ).onSuccess { publicationList ->
            _state.update {
                it.copy(
                    publications = publicationList
                )
            }
        }.onError {
            println("Cannot fetch publications")
        }.onFinally {
            stopLoading()
        }

    }
}