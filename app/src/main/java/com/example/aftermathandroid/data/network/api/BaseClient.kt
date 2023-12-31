package com.example.aftermathandroid.data.network.api

import com.example.aftermathandroid.BuildConfig
import com.example.aftermathandroid.data.network.interceptor.AuthHeaderInterceptor
import com.example.aftermathandroid.data.network.interceptor.AuthResponseInterceptor
import domain.model.ServerException
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import okhttp3.logging.HttpLoggingInterceptor

class BaseClient(
    private val authResponseInterceptor: AuthResponseInterceptor,
    private val authHeaderInterceptor: AuthHeaderInterceptor,
) {
    fun create() = HttpClient(OkHttp) {
        engine {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
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
            validateResponse { response ->
                authResponseInterceptor.invoke(response)
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