package com.talkfrly.multiplatform.data.search.mapper

import com.talkfrly.multiplatform.data.search.dto.PublicationSearchResultDto
import com.talkfrly.multiplatform.data.search.dto.SearchResponseDto
import com.talkfrly.multiplatform.domain.search.SearchResponse
import com.talkfrly.multiplatform.domain.search.SearchResult

fun SearchResponseDto.toDomain(): SearchResponse = SearchResponse(
    publications = publications.map { it.toDomain() },
    page = page,
    limit = limit,
)

fun PublicationSearchResultDto.toDomain(): SearchResult = SearchResult(
    id = id,
    displayName = displayName,
    avatarUrl = avatarUrl,
    type = type,
    title = title,
    tags = tags,
    createdAt = createdAt,
)