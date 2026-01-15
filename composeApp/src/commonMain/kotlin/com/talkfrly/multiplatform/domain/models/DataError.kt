package com.talkfrly.multiplatform.domain.models

import com.talkfrly.multiplatform.domain.core.Error

/**
 * Unified error model used across the entire app.
 * Includes both code (type) and optional message.
 */
sealed class DataError : Error {

    data class Remote(
        val code: HttpErrorCode,
        val error: String? = null,
        val message: String? = null
    ) : DataError()

    data class Local(
        val code: LocalErrorCode,
        val error: String? = null,
        val message: String? = null
    ) : DataError()

    enum class HttpErrorCode {
        REQUEST_TIMEOUT,
        BAD_REQUEST,
        TOO_MANY_REQUESTS,
        NOT_FOUND,
        NO_INTERNET,
        UNAUTHORIZED,
        SERVER_ERROR,
        BAD_GATEWAY,
        SERVICE_UNAVAILABLE,
        GATEWAY_TIMEOUT,
        SERIALIZATION,
        TEMPORARY_REDIRECT,
        UNKNOWN,
    }

    enum class LocalErrorCode {
        DISK_FULL,
        NOT_IMPLEMENTED,
        PERMISSION_DENIED,
    }
}