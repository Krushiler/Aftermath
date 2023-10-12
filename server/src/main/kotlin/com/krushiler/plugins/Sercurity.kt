package com.krushiler.plugins

import com.krushiler.data.repository.UserRepository
import com.krushiler.data.storage.dao.UserDao
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.Principal
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.bearer
import org.koin.ktor.ext.inject

fun Application.configureSecurity() {
    val userRepository: UserRepository by inject()

    install(Authentication) {
        bearer("auth") {
            realm = "Auth"
            authenticate { credential ->
                userRepository.getUserByToken(credential.token)?.let { user ->
                    UserIdPrincipal(user.login)
                }
            }
        }
    }
}

