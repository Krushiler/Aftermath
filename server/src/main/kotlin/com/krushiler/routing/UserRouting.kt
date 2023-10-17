package com.krushiler.routing

import com.krushiler.data.repository.UserRepository
import com.krushiler.util.userLogin
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.userRouting() = route("/user") {
    val userRepository: UserRepository by inject()
    authenticate {
        get {
            try {
                call.respond(userRepository.getUserByLogin(call.userLogin))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
    }
}