package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import com.example.aftermathandroid.data.storage.PreferencesManager
import data.dto.AuthDto
import data.request.LoginRequest
import data.request.RegisterRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val api: BaseApi,
) {
    fun watchAuthState(): Flow<AuthDto?> =
        preferencesManager.tokenFlow.map { it?.let { token -> AuthDto(token) } }

    suspend fun login(name: String, password: String): AuthDto {
        val model = api.login(LoginRequest(name, password))
        preferencesManager.token = model.token
        return model
    }

    suspend fun register(name: String, password: String): AuthDto {
        val model = api.register(RegisterRequest(name, password))
        preferencesManager.token = model.token
        return model
    }

    suspend fun logout() {
        preferencesManager.token = null
    }
}