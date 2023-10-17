package com.krushiler.util

import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authentication

val ApplicationCall.userLogin
    get() =
        authentication.principal<UserIdPrincipal>()?.name ?: throw IllegalArgumentException("Authorization error")