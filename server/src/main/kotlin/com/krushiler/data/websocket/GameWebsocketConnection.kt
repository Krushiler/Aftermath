package com.krushiler.data.websocket

import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.cancellation.CancellationException

class GameWebsocketConnection(private val session: DefaultWebSocketSession) {
    companion object {
        val lastId = AtomicInteger()
    }

    val id = lastId.getAndIncrement()

    suspend fun sendUsersUpdated() {
        try {
            session.send(Frame.Text("users-updated"))
        } catch (_: CancellationException) {
        }
    }

    suspend fun gameStarted() {
        try {
            session.send(Frame.Text("game-started"))
        } catch (_: CancellationException) {
        }
    }

    suspend fun dictionarySelected() {
        try {
            session.send(Frame.Text("dictionary-selected"))
        } catch (_: CancellationException) {
        }
    }
}