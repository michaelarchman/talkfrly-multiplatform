package com.talkfrly.multiplatform.data.publications.repository

import com.talkfrly.multiplatform.data.publications.api.PublicationApi
import com.talkfrly.multiplatform.data.publications.mapper.toDomain
import com.talkfrly.multiplatform.data.publications.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.publication.PublicationFilter
import com.talkfrly.multiplatform.domain.publication.PublicationList

interface PublicationRepository {
    suspend fun getPublications(page: Int, limit: Int, filter: PublicationFilter?): DataResult<PublicationList, DataError.Remote>
}

class PublicationRepositoryImpl(
    private val api: PublicationApi
): PublicationRepository {
    override suspend fun getPublications(page: Int, limit: Int, filter: PublicationFilter?): DataResult<PublicationList, DataError.Remote> {
        return api
            .getPublications(page, limit, filter?.toDto())
            .map { it.toDomain() }
    }
}