package com.example.aftermathandroid.data.network.api

import data.dto.AuthDto
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.UserDto
import data.request.ChangeUserRequest
import data.request.CreateDictionaryRequest
import data.request.LoginRequest
import data.request.RegisterRequest
import data.request.UpdateDictionaryRequest
import data.response.PagedResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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

    suspend fun getMyDictionaries(
        limit: Int = 10,
        offset: Int = 0,
    ): PagedResponse<DictionaryInfoDto> = client.get("/dictionary/my") {
        parameter("limit", limit)
        parameter("offset", offset)
    }.body()

    suspend fun getGeneralDictionaries(
        limit: Int = 10,
        offset: Int = 0,
    ): PagedResponse<DictionaryInfoDto> = client.get("/dictionary/all") {
        parameter("limit", limit)
        parameter("offset", offset)
    }.body()

    suspend fun createDictionary(request: CreateDictionaryRequest): DictionaryDto = client.post("/dictionary/create") {
        setBody(request)
    }.body()

    suspend fun updateDictionary(request: UpdateDictionaryRequest): DictionaryDto = client.post("/dictionary/edit") {
        setBody(request)
    }.body()
}