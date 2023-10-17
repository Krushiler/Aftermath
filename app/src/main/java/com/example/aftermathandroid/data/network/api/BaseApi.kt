package com.example.aftermathandroid.data.network.api

import data.dto.AuthDto
import data.dto.UserDto
import data.request.ChangeUserRequest
import data.request.LoginRequest
import data.request.RegisterRequest
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class BaseApi(private val client: HttpClient) {
    suspend fun login(request: LoginRequest): AuthDto = client.post("/auth/login") {
        setBody(request)
    }.body()

    suspend fun register(request: RegisterRequest): AuthDto = client.post("/auth/register") {
        setBody(request)
    }.body()

    suspend fun getUser(): UserDto = client.get("/user").body()

    suspend fun updateUser(request: ChangeUserRequest): UserDto = client.patch("/user") {
        setBody(request)
    }.body()
}