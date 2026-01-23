package com.talkfrly.multiplatform.data.core

import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import com.talkfrly.multiplatform.domain.core.DataResult
import com.talkfrly.multiplatform.domain.models.DataError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.http.takeFrom
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

/**
 * Generic HTTP request with optional auth header.
 */
suspend inline fun <reified T> makeRequest(
    httpClient: HttpClient,
    preferencesRepository: PreferencesRepository,
    urlString: String,
    httpMethod: HttpMethod = HttpMethod.Get,
    body: Any? = null,
    headers: Map<String, String> = emptyMap(),
    queryParams: Map<String, Any> = emptyMap(),
    requireAuth: Boolean = false,
): DataResult<T, DataError.Remote> {
    return try {
        delay(600L)
        val response = httpClient.request {
            method = httpMethod

            url {
                takeFrom("$BASE_API$urlString")
                queryParams.forEach { (key, value) ->
                    parameters.append(key, value.toString())
                }
            }

            headers.forEach { (key, value) ->
                this.headers.append(key, value)
            }

            body?.let {
                contentType(ContentType.Application.Json)
                setBody(it)
            }
        }

        if (T::class == Unit::class) {
            return DataResult.ResultSuccess(Unit as T)
        }

        println("HTTP RESPONSE STATUS: ${response.status}")
        println("HTTP RESPONSE BODY: ${response.bodyAsText()}")
        println("HTTP RESPONSE HEADERS: $")

        handleResponse(response, preferencesRepository)

    } catch (e: SocketTimeoutException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.REQUEST_TIMEOUT,
                message = "Przekroczono czas odpowiedzi: ${e.message}"
            )
        )
    } catch (e: UnresolvedAddressException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.NO_INTERNET,
                message = "Brak dostępu do internetu: ${e.message}"
            )
        )
    } catch (e: SerializationException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.SERIALIZATION,
                message = "`Błąd serializacji: ${e.message}"
            ))
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        DataResult.ResultError(
            error = DataError.Remote(
                code = DataError.HttpErrorCode.UNKNOWN,
                message = e.message ?: "Wystąpił nieznany błąd: $e",
            )
        )
    }
}

/**
 * HTTP request for form-urlencoded payloads.
 */
suspend inline fun <reified T> makeFormRequest(
    httpClient: HttpClient,
    urlString: String,
    method: HttpMethod = HttpMethod.Post,
    formParameters: Map<String, String>,
    preferencesRepository: PreferencesRepository
): DataResult<T, DataError.Remote> {
    return try {
        delay(600L)
        val response = httpClient.request {
            this.url("${BASE_API}$urlString")
            this.method = method
            contentType(ContentType.Application.FormUrlEncoded)
            setBody(
                FormDataContent(
                    Parameters.build {
                        formParameters.forEach { (k, v) -> append(k, v) }
                    }
                )
            )
        }

        handleResponse(response, preferencesRepository)
    } catch (e: SocketTimeoutException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.REQUEST_TIMEOUT,
                message = "Przekroczono czas odpowiedzi: ${e.message}"
            )
        )
    } catch (e: UnresolvedAddressException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.NO_INTERNET,
                message = "Brak dostępu do internetu: ${e.message}"
            )
        )
    } catch (e: SerializationException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.SERIALIZATION,
                message = "`Błąd serializacji: ${e.message}"
            ))
    } catch (e: Exception) {
        currentCoroutineContext().ensureActive()
        DataResult.ResultError(
            error = DataError.Remote(
                code = DataError.HttpErrorCode.UNKNOWN,
                message = e.message ?: "Wystąpił nieznany błąd: $e",
            )
        )
    }
}

/**
 * Handles HTTP responses and converts them to DataResult.
 * Parses JSON error body when available.
 */
suspend inline fun <reified T> handleResponse(
    response: HttpResponse,
    preferencesRepository: PreferencesRepository,
): DataResult<T, DataError.Remote> {
    val statusCode = response.status.value
    val bodyText = response.bodyAsText().trim()

    return try {
        when (statusCode) {
            in 200..299 -> {
                // Success response
                DataResult.ResultSuccess(response.body())
            }

            401 -> {
                DataResult.ResultError(
                    DataError.Remote(
                        code = DataError.HttpErrorCode.UNAUTHORIZED,
                        message = "Unauthorized"
                    )
                )
            }

            else -> {
                // Error response → try to parse the error body
                val errorDto = try {
                    Json.decodeFromString<ErrorDto>(bodyText)
                } catch (_: Exception) {
                    ErrorDto(status = statusCode, message = null, error = null)
                }

                val mappedCode = mapHttpCodeToEnum(statusCode)
                val message = errorDto.message ?: "Wystąpił błąd ($statusCode)"
                val errorType = errorDto.error

                println("SERVER ERROR [$statusCode]: $message")

                DataResult.ResultError(
                    DataError.Remote(
                        code = mappedCode,
                        error = errorType,
                        message = message
                    )
                )
            }
        }
    } catch (e: SerializationException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.SERIALIZATION,
                message = "Błąd przetwarzania danych: $e"
            )
        )
    } catch (e: SocketTimeoutException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.REQUEST_TIMEOUT,
                message = "Przekroczono czas oczekiwania na odpowiedź serwera: $e"
            )
        )
    } catch (e: UnresolvedAddressException) {
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.NO_INTERNET,
                message = "Brak połączenia z internetem: $e"
            )
        )
    } catch (e: Exception) {
        e.printStackTrace()
        DataResult.ResultError(
            DataError.Remote(
                code = DataError.HttpErrorCode.UNKNOWN,
                message = e.message ?: "Wystąpił nieznany błąd: $e"
            )
        )
    }
}

fun mapHttpCodeToEnum(status: Int): DataError.HttpErrorCode {
    return when (status) {
        307 -> DataError.HttpErrorCode.TEMPORARY_REDIRECT
        400 -> DataError.HttpErrorCode.BAD_REQUEST
        404 -> DataError.HttpErrorCode.NOT_FOUND
        408 -> DataError.HttpErrorCode.REQUEST_TIMEOUT
        429 -> DataError.HttpErrorCode.TOO_MANY_REQUESTS
        500 -> DataError.HttpErrorCode.SERVER_ERROR
        502 -> DataError.HttpErrorCode.BAD_GATEWAY
        503 -> DataError.HttpErrorCode.SERVICE_UNAVAILABLE
        504 -> DataError.HttpErrorCode.GATEWAY_TIMEOUT
        else -> DataError.HttpErrorCode.UNKNOWN
    }
}
