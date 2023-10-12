package com.krushiler.di

import com.krushiler.data.repository.UserRepository
import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.database.DatabaseFactory
import org.koin.dsl.module

val dataModule = module {
    single { DatabaseFactory.create() }
    single { UserDao(get()) }
    single { UserRepository(get()) }
}