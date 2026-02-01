package com.talkfrly.multiplatform.data.uploads.api

import com.talkfrly.multiplatform.data.core.makeRequest
import com.talkfrly.multiplatform.data.uploads.dto.PresignUploadRequestDto
import com.talkfrly.multiplatform.data.uploads.dto.PresignUploadResponseDto
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.client.statement.bodyAsText

interface UploadApi {
    suspend fun presignUpload(filename: String): DataResult<PresignUploadResponseDto, DataError.Remote>
    suspend fun uploadToPresignedUrl(
        uploadUrl: String,
        bytes: ByteArray,
        contentType: String? = null
    ): DataResult<Unit, DataError.Remote>
}

class UploadApiImpl(
    private val httpClient: HttpClient
) : UploadApi {
    override suspend fun presignUpload(filename: String): DataResult<PresignUploadResponseDto, DataError.Remote> {
        return makeRequest(
            httpMethod = HttpMethod.Post,
            httpClient = httpClient,
            urlString = "/uploads/presign",
            body = PresignUploadRequestDto(filename)
        )
    }

    override suspend fun uploadToPresignedUrl(
        uploadUrl: String,
        bytes: ByteArray,
        contentType: String?
    ): DataResult<Unit, DataError.Remote> {
        return try {
            val response = httpClient.request {
                method = HttpMethod.Put
                url(uploadUrl)
                contentType(contentType?.let { ContentType.parse(it) } ?: ContentType.Application.OctetStream)
                setBody(bytes)
            }
            if (response.status.value in 200..299) {
                DataResult.ResultSuccess(Unit)
            } else {
                DataResult.ResultError(
                    DataError.Remote(
                        code = DataError.HttpErrorCode.UNKNOWN,
                        message = "Upload failed (${response.status.value}): ${response.bodyAsText()}"
                    )
                )
            }
        } catch (e: Exception) {
            DataResult.ResultError(
                DataError.Remote(
                    code = DataError.HttpErrorCode.UNKNOWN,
                    message = e.message ?: "Upload failed: $e"
                )
            )
        }
    }
}
