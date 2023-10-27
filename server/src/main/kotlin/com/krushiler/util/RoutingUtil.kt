package com.krushiler.util

import com.krushiler.domain.model.PagingData
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.authentication

val ApplicationCall.userLogin
    get() = authentication.principal<UserIdPrincipal>()?.name ?: throw IllegalArgumentException("Authorization error")

val ApplicationCall.pagingData
    get() = PagingData(request.queryParameters["limit"]?.toInt() ?: 10, request.queryParameters["offset"]?.toInt() ?: 0)
