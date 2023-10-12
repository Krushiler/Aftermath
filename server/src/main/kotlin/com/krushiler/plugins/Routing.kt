package com.krushiler.plugins

import com.krushiler.routing.auth.authRouting
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText { "Server is working" }
        }
        authRouting()
    }
}
