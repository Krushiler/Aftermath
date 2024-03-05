package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyListRepository @Inject constructor(private val baseApi: BaseApi) {
    suspend fun getLobbies() = baseApi.getLobbies()

    suspend fun createLobby() = baseApi.createLobby()
}