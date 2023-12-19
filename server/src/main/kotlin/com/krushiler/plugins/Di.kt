package com.krushiler.plugins

import com.krushiler.di.dataModule
import com.krushiler.domain.interactor.DictionaryInteractor
import io.ktor.server.application.Application
import io.ktor.server.application.install
import kotlinx.coroutines.launch
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin

fun Application.configureDi() {
    install(Koin) {
        modules(dataModule)
    }

    launch {
        val dictionaryInteractor: DictionaryInteractor by inject()
        dictionaryInteractor.createInitialDictionaries()
    }
}