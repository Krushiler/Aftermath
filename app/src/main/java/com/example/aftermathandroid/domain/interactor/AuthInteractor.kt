package com.example.aftermathandroid.domain.interactor

import com.example.aftermathandroid.data.repository.AuthRepository
import com.example.aftermathandroid.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val coroutineScope: CoroutineScope,
) {
    init {
        coroutineScope.launch {
            authRepository.watchAuthState().collect {
                if (it == null) userRepository.clear()
                else userRepository.fetchUser()
            }
        }
    }

    fun watchAuthState() = authRepository.watchAuthState()

    fun watchUser() = userRepository.watchUser()

    suspend fun logout() = authRepository.logout()

    suspend fun login(login: String, password: String) = authRepository.login(login, password)

    suspend fun register(login: String, password: String) = authRepository.register(login, password)

    suspend fun updateUser(name: String, avatar: String?) = userRepository.updateUser(name, avatar)
}