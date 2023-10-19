package com.krushiler

import com.krushiler.plugins.configureDi
import com.krushiler.plugins.configureRouting
import com.krushiler.plugins.configureSecurity
import com.krushiler.plugins.configureSerialization
import com.krushiler.plugins.configureSockets
import com.krushiler.plugins.configureStatusPages
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDi()
    configureSerialization()
    configureSecurity()
    configureStatusPages()
    configureSockets()
    configureRouting()
}
