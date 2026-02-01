package com.talkfrly.multiplatform.data.uploads.repository

import com.talkfrly.multiplatform.data.uploads.api.UploadApi
import com.talkfrly.multiplatform.data.uploads.guessFileNameFromUri
import com.talkfrly.multiplatform.data.uploads.readBytesFromUri
import com.talkfrly.multiplatform.domain.core.DataError
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.core.onError
import com.talkfrly.multiplatform.domain.core.onSuccess

interface UploadRepository {
    suspend fun uploadImage(uri: String): DataResult<String, DataError.Remote>
}

class UploadRepositoryImpl(
    private val uploadApi: UploadApi
) : UploadRepository {
    override suspend fun uploadImage(uri: String): DataResult<String, DataError.Remote> {
        val filename = guessFileNameFromUri(uri)
        val bytes = readBytesFromUri(uri)
        if (bytes.isEmpty()) {
            return DataResult.ResultError(
                DataError.Remote(
                    code = DataError.HttpErrorCode.UNKNOWN,
                    message = "Failed to read image data"
                )
            )
        }

        return when (val presign = uploadApi.presignUpload(filename)) {
            is DataResult.ResultError -> DataResult.ResultError(presign.error)
            is DataResult.ResultSuccess -> {
                val contentType = guessContentType(filename)
                when (val upload = uploadApi.uploadToPresignedUrl(presign.data.uploadUrl, bytes, contentType)) {
                    is DataResult.ResultError -> DataResult.ResultError(upload.error)
                    is DataResult.ResultSuccess -> {
                        val fileUrl = presign.data.fileUrl
                        if (fileUrl.isBlank()) {
                            DataResult.ResultError(
                                DataError.Remote(
                                    code = DataError.HttpErrorCode.UNKNOWN,
                                    message = "Upload completed, but file URL is empty"
                                )
                            )
                        } else {
                            DataResult.ResultSuccess(fileUrl)
                        }
                    }
                }
            }
        }
    }
}

private fun guessContentType(filename: String): String? {
    val ext = filename.substringAfterLast('.', "").lowercase()
    return when (ext) {
        "jpg", "jpeg" -> "image/jpeg"
        "png" -> "image/png"
        "gif" -> "image/gif"
        "webp" -> "image/webp"
        "heic" -> "image/heic"
        else -> null
    }
}
