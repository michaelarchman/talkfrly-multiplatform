package com.talkfrly.multiplatform.data.publications.api

import com.talkfrly.multiplatform.data.core.BASE_API
import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.data.publications.dto.PublicationFilterDto
import com.talkfrly.multiplatform.data.publications.dto.PublicationListResponseDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.http.HttpMethod

interface PublicationApi {
    suspend fun getPublications(page: Int, limit: Int, filter: PublicationFilterDto?): DataResult<PublicationListResponseDto, DataError.Remote>
}

class PublicationApiImpl(
    private val httpClient: HttpClient,
    private val preferencesRepository: PreferencesRepository,
): PublicationApi {
    override suspend fun getPublications(
        page: Int,
        limit: Int,
        filter: PublicationFilterDto?
    ): DataResult<PublicationListResponseDto, DataError.Remote> {
        return makeRequest(
            requireAuth = true,
            httpMethod = HttpMethod.Get,
            httpClient = httpClient,
            queryParams = mapOf(
                "page" to page.toString(),
                "limit" to limit.toString(),
                "channel_id" to (filter?.channelId ?: ""),
                "topic_id" to (filter?.topicId ?: ""),
                "thread_id" to (filter?.threadId ?: ""),
                "module_type" to (filter?.moduleType ?: ""),
            ).filterValues { it.isNotEmpty() },
            preferencesRepository = preferencesRepository,
            urlString = "$BASE_API/publications",
        )
    }
}