package com.krushiler.plugins

import com.krushiler.routing.authRouting
import com.krushiler.routing.dictionaryRouting
import com.krushiler.routing.gameRouting
import com.krushiler.routing.staticRouting
import com.krushiler.routing.userRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText { "Welcome to aftermath" }
        }
        staticRouting()
        authRouting()
        userRouting()
        dictionaryRouting()
        gameRouting()
    }
}
