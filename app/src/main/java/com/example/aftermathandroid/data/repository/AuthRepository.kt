package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import com.example.aftermathandroid.data.storage.PreferencesManager
import data.dto.AuthDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface AuthRepository {
    fun watchAuthState(): Flow<AuthDto?>

    suspend fun login(name: String, password: String): AuthDto

    suspend fun register(name: String, password: String): AuthDto

    suspend fun logout()
}

class AuthRepositoryImpl @Inject constructor(
    private val preferencesManager: PreferencesManager,
    private val api: BaseApi,
) : AuthRepository {
    override fun watchAuthState(): Flow<AuthDto?> =
        preferencesManager.tokenFlow.map { it?.let { token -> AuthDto(token) } }

    override suspend fun login(name: String, password: String): AuthDto {
        val model = api.login(name, password)
        preferencesManager.token = model.token
        return model
    }

    override suspend fun register(name: String, password: String): AuthDto {
        val model = api.login(name, password)
        preferencesManager.token = model.token
        return model
    }

    override suspend fun logout() {
        preferencesManager.token = null
    }
}