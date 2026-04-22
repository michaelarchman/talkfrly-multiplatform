package com.talkfrly.multiplatform.data.core

import com.talkfrly.multiplatform.data.preferences.repository.PreferencesRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine, preferencesRepository: PreferencesRepository): HttpClient {
        return HttpClient(engine) {
            install(HttpCookies) {
                storage = preferencesRepository
            }
            install(HttpCallValidator) {
                validateResponse { response ->
                    if (response.status.value == 401) {
                        preferencesRepository.clearAccessToken()
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    json = Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 10_000L
                requestTimeoutMillis = 10_000L
            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client log: $message")
                    }
                }
                level = LogLevel.BODY
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}