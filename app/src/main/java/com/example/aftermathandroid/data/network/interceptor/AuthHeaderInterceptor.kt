package com.example.aftermathandroid.data.network.interceptor

import com.example.aftermathandroid.data.storage.preferences.PreferencesManager
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthHeaderInterceptor @Inject constructor(
    private val preferencesManager: PreferencesManager
) {
    fun install(auth: Auth) = auth.bearer {
        loadTokens {
            preferencesManager.token?.let { token ->
                BearerTokens(token, token)
            }
        }
        refreshTokens {
            preferencesManager.token?.let { token ->
                BearerTokens(token, token)
            }
        }
    }
}