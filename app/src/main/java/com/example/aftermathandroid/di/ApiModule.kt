package com.example.aftermathandroid.di

import com.example.aftermathandroid.data.network.api.BaseApi
import com.example.aftermathandroid.data.network.api.BaseClient
import com.example.aftermathandroid.data.network.interceptor.AuthHeaderInterceptor
import com.example.aftermathandroid.data.network.interceptor.AuthResponseInterceptor
import com.example.aftermathandroid.data.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Singleton
    @Provides
    fun provideBaseClient(
        authResponseInterceptor: AuthResponseInterceptor,
        authHeaderInterceptor: AuthHeaderInterceptor
    ): HttpClient = BaseClient(
        authResponseInterceptor,
        authHeaderInterceptor
    ).create()

    @Singleton
    @Provides
    fun provideBaseApi(client: HttpClient): BaseApi = BaseApi(client)
}