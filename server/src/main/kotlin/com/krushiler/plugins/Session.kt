package com.krushiler.plugins

import data.dto.GameSession
import io.ktor.server.application.Application
import io.ktor.server.application.ApplicationCallPipeline
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions

fun Application.configureSession() {
    install(Sessions) {
        cookie<GameSession>("GAME_SESSION")
    }
}