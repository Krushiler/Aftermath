package com.krushiler.di

import com.krushiler.data.repository.DictionaryRepository
import com.krushiler.data.repository.UserRepository
import com.krushiler.data.storage.dao.DictionaryDao
import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.database.DatabaseFactory
import com.krushiler.domain.interactor.DictionaryInteractor
import org.koin.dsl.module

val dataModule = module {
    single { DatabaseFactory.create() }
    single { UserDao(get()) }
    single { UserRepository(get()) }
    single { DictionaryDao(get()) }
    single { DictionaryRepository(get(), get()) }
    single { DictionaryInteractor(get(), get()) }
}