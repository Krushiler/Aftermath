package com.example.aftermathandroid.data.network.interceptor

import com.example.aftermathandroid.data.storage.PreferencesManager
import io.ktor.client.plugins.ResponseValidator
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthResponseInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) : ResponseValidator {
    override suspend fun invoke(response: HttpResponse) {
        if (response.status.value == 401) {
            preferencesManager.token = null
        }
    }
}