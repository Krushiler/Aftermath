package com.example.aftermathandroid.data.network.api

import data.dto.AuthDto
import data.request.LoginRequest
import data.request.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class BaseApi(private val client: HttpClient) {
    suspend fun login(login: String, password: String): AuthDto = client.post("/auth/login") {
        setBody(LoginRequest(login, password))
    }.body()

    suspend fun register(login: String, password: String): AuthDto = client.post("/auth/register") {
        setBody(RegisterRequest(login, password))
    }.body()
}