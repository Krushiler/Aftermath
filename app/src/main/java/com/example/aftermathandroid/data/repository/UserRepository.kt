package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import data.dto.UserDto
import data.request.ChangeUserRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val api: BaseApi
) {
    private val userStateFlow = MutableStateFlow<UserDto?>(null)
    fun watchUser(): StateFlow<UserDto?> = userStateFlow

    suspend fun fetchUser() = try {
        userStateFlow.value = api.getUser()
    } catch (_: Exception) {
    }

    suspend fun updateUser(name: String, avatar: String?) {
        userStateFlow.value = api.updateUser(ChangeUserRequest(name, avatar))
    }

    suspend fun clear() {}
}