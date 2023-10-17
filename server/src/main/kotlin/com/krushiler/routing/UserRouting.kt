package com.krushiler.routing

import com.krushiler.data.repository.UserRepository
import com.krushiler.util.userLogin
import data.request.ChangeUserRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.patch
import io.ktor.server.routing.route
import org.koin.ktor.ext.inject

fun Routing.userRouting() = route("/user") {
    val userRepository: UserRepository by inject()
    authenticate {
        patch {
            try {
                val login = call.userLogin
                val request = call.receive<ChangeUserRequest>()
                userRepository.updateUser(login, request.name, request.avatar)
                call.respond(userRepository.getUserByLogin(login))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
        get {
            try {
                call.respond(userRepository.getUserByLogin(call.userLogin))
            } catch (e: Exception) {
                call.respondText(e.localizedMessage, status = HttpStatusCode.BadRequest)
            }
        }
    }
}