package com.example.aftermathandroid.data.network.api

import com.example.aftermathandroid.BuildConfig
import com.example.aftermathandroid.data.network.interceptor.AuthHeaderInterceptor
import com.example.aftermathandroid.data.network.interceptor.AuthResponseInterceptor
import domain.model.ServerException
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BaseClient(
    private val authResponseInterceptor: AuthResponseInterceptor,
    private val authHeaderInterceptor: AuthHeaderInterceptor,
) {
    fun create() = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                allowStructuredMapKeys = true
                allowSpecialFloatingPointValues = true
                ignoreUnknownKeys = true
            })
        }
        install(Auth) {
            authHeaderInterceptor.install(this)
        }
        HttpResponseValidator {
            validateResponse(authResponseInterceptor)
            validateResponse { response ->
                if (!response.status.isSuccess()) {
                    throw ServerException(response.bodyAsText())
                }
            }
        }
        defaultRequest {
            url(BuildConfig.BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }
}