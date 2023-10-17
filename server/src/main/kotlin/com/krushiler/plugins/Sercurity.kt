package com.krushiler.plugins

import com.krushiler.data.repository.UserRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val userRepository: UserRepository by inject()

    install(Authentication) {
        bearer {
            realm = "Auth"
            authenticate { credential ->
                userRepository.getUserByTokenOrNull(credential.token)?.let { user ->
                    UserIdPrincipal(user.login)
                }
            }
        }
    }
}
