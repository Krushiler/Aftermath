package com.krushiler.data.websocket

import data.dto.GameServerAction
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.sendSerialized
import java.util.concurrent.atomic.AtomicInteger

class GameWebsocketConnection(private val session: DefaultWebSocketServerSession) {
    companion object {
        val lastId = AtomicInteger()
    }

    val id = lastId.getAndIncrement()

    suspend fun sendAction(action: GameServerAction) {
        session.sendSerialized(action)
    }
}