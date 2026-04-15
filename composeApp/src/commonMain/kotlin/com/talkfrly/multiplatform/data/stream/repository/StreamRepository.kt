package com.talkfrly.multiplatform.data.stream.repository

import com.talkfrly.multiplatform.data.stream.api.StreamApi
import com.talkfrly.multiplatform.data.stream.dto.StreamListRequestDto
import com.talkfrly.multiplatform.data.stream.mapper.toDomain
import com.talkfrly.multiplatform.data.stream.mapper.toDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.map
import com.talkfrly.multiplatform.domain.stream.StreamResponse
import com.talkfrly.multiplatform.domain.stream.StreamKey
import com.talkfrly.multiplatform.domain.stream.StreamList
import com.talkfrly.multiplatform.domain.stream.StreamRequest
import com.talkfrly.multiplatform.domain.stream.StreamStopResponse
import com.talkfrly.multiplatform.domain.stream.StreamViewerResponse

interface StreamRepository {
    suspend fun streamList(
        page: Int,
        limit: Int
    ): DataResult<StreamList, DataError.Remote>

    suspend fun createStream(
        streamRequest: StreamRequest
    ): DataResult<StreamResponse, DataError.Remote>

    suspend fun getCurrentStream(): DataResult<StreamResponse, DataError.Remote>
    suspend fun getStreamById(id: String): DataResult<StreamViewerResponse, DataError.Remote>

    suspend fun updateStream(
        id: String,
        streamRequest: StreamRequest
    ): DataResult<StreamResponse, DataError.Remote>

    suspend fun deleteStream(id: String): DataResult<Unit, DataError.Remote>
    suspend fun setupLiveInput(id: String): DataResult<StreamKey, DataError.Remote>
    suspend fun stop(id: String): DataResult<StreamStopResponse, DataError.Remote>
    suspend fun getStreamKey(id: String): DataResult<StreamKey, DataError.Remote>
}

class StreamRepositoryImpl(
    private val api: StreamApi,
) : StreamRepository {
    override suspend fun streamList(
        page: Int,
        limit: Int
    ): DataResult<StreamList, DataError.Remote> {
        return api
            .streamList(
                StreamListRequestDto(
                    page = page,
                    limit = limit,
                )
            )
            .map { it.toDomain() }
    }

    override suspend fun createStream(
        streamRequest: StreamRequest
    ): DataResult<StreamResponse, DataError.Remote> {
        return api
            .createStream(streamRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun getCurrentStream(): DataResult<StreamResponse, DataError.Remote> {
        return api
            .getCurrentStream()
            .map { it.toDomain() }
    }

    override suspend fun getStreamById(id: String): DataResult<StreamViewerResponse, DataError.Remote> {
        return api
            .getStreamById(id)
            .map { it.toDomain() }
    }

    override suspend fun updateStream(
        id: String,
        streamRequest: StreamRequest
    ): DataResult<StreamResponse, DataError.Remote> {
        return api
            .updateStream(id, streamRequest.toDto())
            .map { it.toDomain() }
    }

    override suspend fun deleteStream(id: String): DataResult<Unit, DataError.Remote> {
        return api.deleteStream(id)
    }

    override suspend fun setupLiveInput(id: String): DataResult<StreamKey, DataError.Remote> {
        return api
            .setupLiveInput(id)
            .map { it.toDomain() }
    }

    override suspend fun stop(id: String): DataResult<StreamStopResponse, DataError.Remote> {
        return api
            .stop(id)
            .map { it.toDomain() }
    }

    override suspend fun getStreamKey(id: String): DataResult<StreamKey, DataError.Remote> {
        return api
            .getStreamKey(id)
            .map { it.toDomain() }
    }
}
