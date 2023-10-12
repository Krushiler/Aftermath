package com.krushiler.plugins

import com.krushiler.di.dataModule
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.ktor.plugin.Koin

fun Application.configureDi() {
    install(Koin) {
        modules(dataModule)
    }
}