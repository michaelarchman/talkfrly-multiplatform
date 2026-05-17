package com.talkfrly.multiplatform.data.search.repository

import com.talkfrly.multiplatform.data.search.api.SearchApi
import com.talkfrly.multiplatform.data.search.mapper.toDomain
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.search.SearchResponse

interface SearchRepository {
    suspend fun search(query: String, page: Int, limit: Int): DataResult<SearchResponse, DataError.Remote>
}

class SearchRepositoryImpl(
    private val api: SearchApi,
) : SearchRepository {
    override suspend fun search(
        query: String,
        page: Int,
        limit: Int,
    ): DataResult<SearchResponse, DataError.Remote> {
        return api.search(query, page, limit).map { it.toDomain() }
    }
}