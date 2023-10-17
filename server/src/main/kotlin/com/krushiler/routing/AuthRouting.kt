package com.krushiler.routing

import com.krushiler.data.repository.UserRepository
import data.request.LoginRequest
import data.request.RegisterRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.authRouting() = route("/auth") {
    val userRepository: UserRepository by inject()
    post("/login") {
        try {
            val login = call.receive<LoginRequest>()
            val auth = userRepository.login(login.name, login.password)
            call.respond(auth)
        } catch (e: Exception) {
            call.respondText(e.localizedMessage, status = HttpStatusCode.Unauthorized)
        }
    }
    post("/register") {
        try {
            val login = call.receive<RegisterRequest>()
            val auth = userRepository.register(login.name, login.password)
            call.respond(auth)
        } catch (e: Exception) {
            call.respondText(e.localizedMessage, status = HttpStatusCode.Unauthorized)
        }
    }
}