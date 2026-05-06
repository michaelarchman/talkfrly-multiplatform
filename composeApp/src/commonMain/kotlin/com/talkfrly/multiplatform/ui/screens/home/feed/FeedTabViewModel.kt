package com.talkfrly.multiplatform.ui.screens.home.feed

import androidx.lifecycle.viewModelScope
import com.talkfrly.multiplatform.BaseViewModel
import com.talkfrly.multiplatform.data.feed.repository.FeedRepository
import com.talkfrly.multiplatform.data.publications.repository.PublicationRepository
import com.talkfrly.multiplatform.data.ranking.repository.RankingRepository
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onFinally
import com.talkfrly.multiplatform.domain.core.onSuccess
import com.talkfrly.multiplatform.domain.feed.FeedItem
import com.talkfrly.multiplatform.domain.ranking.RankedItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FeedTabViewModel(
    private val feedRepository: FeedRepository,
    private val publicationRepository: PublicationRepository,
    private val rankingRepository: RankingRepository,
) : BaseViewModel() {
    private val _state = MutableStateFlow(FeedTabState())
    val state: StateFlow<FeedTabState> get() = _state

    fun onIntent(intent: FeedTabIntent) {
        when (intent) {
            is FeedTabIntent.GetFeed -> {
                if (intent.page == 1) _state.update { it.copy(visiblePublications = emptyList(), isLoading = true) }
                fetchFeed(intent.page, intent.limit)
            }
            is FeedTabIntent.LikePublication -> likePublication(intent.publicationId)
            is FeedTabIntent.UnlikePublication -> unlikePublication(intent.publicationId)
            is FeedTabIntent.VoteRankingItem -> voteRankingItem(intent.publicationId, intent.itemId, intent.value)
        }
    }

    private fun likePublication(publicationId: String) = viewModelScope.launch {
        _state.update { current ->
            current.copy(
                visiblePublications = current.visiblePublications.map { item ->
                    if (item.id == publicationId) item.copy(likedByUser = true, likeCount = item.likeCount + 1)
                    else item
                }
            )
        }
        publicationRepository.likePublicationById(publicationId)
            .onError {
                _state.update { current ->
                    current.copy(
                        visiblePublications = current.visiblePublications.map { item ->
                            if (item.id == publicationId) item.copy(likedByUser = false, likeCount = item.likeCount - 1)
                            else item
                        }
                    )
                }
            }
    }

    private fun unlikePublication(publicationId: String) = viewModelScope.launch {
        _state.update { current ->
            current.copy(
                visiblePublications = current.visiblePublications.map { item ->
                    if (item.id == publicationId) item.copy(likedByUser = false, likeCount = item.likeCount - 1)
                    else item
                }
            )
        }
        publicationRepository.unlikePublicationById(publicationId)
            .onError {
                _state.update { current ->
                    current.copy(
                        visiblePublications = current.visiblePublications.map { item ->
                            if (item.id == publicationId) item.copy(likedByUser = true, likeCount = item.likeCount + 1)
                            else item
                        }
                    )
                }
            }
    }

    private fun fetchFeed(page: Int, limit: Int) = viewModelScope.launch {
        if (page == 1) _state.update { it.copy(visiblePublications = emptyList(), isLoading = true) }
        startLoading()
        feedRepository.getFeed(page = page, limit = limit)
            .onSuccess { newFeed ->
                _state.update { current ->
                    val combined = if (page == 1) {
                        newFeed.publications
                    } else {
                        (current.visiblePublications + newFeed.publications)
                            .distinctBy { it.id }
                    }
                    current.copy(
                        feed = newFeed,
                        visiblePublications = combined,
                        hasNextPage = newFeed.publications.size >= newFeed.limit
                    )
                }
            }.onError { error ->
                println("Error during Feed Fetch, $error")
            }.onFinally {
                _state.update { it.copy(isLoading = false) }
                stopLoading()
            }
    }

    private fun voteRankingItem(
        publicationId: String,
        itemId: String,
        value: Int,
    ) = viewModelScope.launch {
        val previousPublications = state.value.visiblePublications

        _state.update { current ->
            current.copy(
                visiblePublications = current.visiblePublications.map { item ->
                    if (item.id == publicationId) {
                        item.withUpdatedRankingVote(itemId = itemId, nextVote = value)
                    } else {
                        item
                    }
                }
            )
        }

        val result = if (value == 0) {
            rankingRepository.removeVote(publicationId, itemId)
        } else {
            rankingRepository.vote(publicationId, itemId, value)
        }

        result.onError {
            _state.update { current ->
                current.copy(visiblePublications = previousPublications)
            }
        }
    }
}

private fun FeedItem.withUpdatedRankingVote(
    itemId: String,
    nextVote: Int,
): FeedItem {
    val currentRanking = ranking ?: return this
    val updatedItems = currentRanking.items.map { rankedItem ->
        if (rankedItem.id != itemId) return@map rankedItem
        rankedItem.withUpdatedVote(nextVote, currentRanking.noNegativeScores)
    }

    return copy(
        ranking = currentRanking.copy(items = updatedItems),
    )
}

private fun RankedItem.withUpdatedVote(
    nextVote: Int,
    noNegativeScores: Boolean,
): RankedItem {
    val previousVote = userVote ?: 0
    val nextUserVote = nextVote.takeIf { it != 0 }
    val scoreDelta = nextVote - previousVote
    val updatedScore = if (noNegativeScores) {
        (score + scoreDelta).coerceAtLeast(0)
    } else {
        score + scoreDelta
    }

    return copy(
        score = updatedScore,
        userVote = nextUserVote,
    )
}
