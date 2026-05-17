package com.talkfrly.multiplatform.data.feed.repository

import com.talkfrly.multiplatform.data.feed.api.FeedApi
import com.talkfrly.multiplatform.data.feed.mapper.toDomain
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.feed.Feed

interface FeedRepository {
    suspend fun getFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote>
    suspend fun getPopularFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote>
    suspend fun getThreadFeed(threadId: String, page: Int, limit: Int): DataResult<Feed, DataError.Remote>
}

class FeedRepositoryImpl(
    @Suppress("UNUSED_PARAMETER")
    private val api: FeedApi,
) : FeedRepository {
    override suspend fun getFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote> {
       return api
           .getFeed(page, limit)
           .map{ it.toDomain() }
    }

    override suspend fun getPopularFeed(page: Int, limit: Int): DataResult<Feed, DataError.Remote> {
        return api
            .getPopularFeed(page, limit)
            .map { it.toDomain() }
    }

    override suspend fun getThreadFeed(threadId: String, page: Int, limit: Int): DataResult<Feed, DataError.Remote> {
        return api
            .getThreadFeed(threadId, page, limit)
            .map { it.toDomain() }
    }
}