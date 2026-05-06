package com.talkfrly.multiplatform.domain.search

data class SearchResult(
    val id: String,
    val displayName: String? = null,
    val avatarUrl: String? = null,
    val type: String,
    val title: String,
    val tags: List<String>,
    val createdAt: String,
)

data class SearchResponse(
    val publications: List<SearchResult>,
    val page: Int,
    val limit: Int,
)