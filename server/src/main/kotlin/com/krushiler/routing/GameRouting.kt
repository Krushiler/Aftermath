package com.krushiler.routing

import com.krushiler.domain.interactor.GameInteractor
import data.dto.GameClientAction
import data.dto.GameClientActionType
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import io.ktor.server.sessions.sessionId
import io.ktor.server.sessions.sessions
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.ktor.ext.inject

fun Routing.gameRouting() = route("/game") {
    val gameInteractor: GameInteractor by inject()

    webSocket {
        incoming.consumeEach {
            if (it is Frame.Text) {
                val text = it.readText()
                val (command, args) = text.split(" ", limit = 2)
                when (command) {
                    "connect" -> {
                        gameInteractor.registerConnection(args, this)
                    }
                }
            }
        }
    }
}

private fun Route.gameWebSocket(
    handleFrame: suspend (
        socket: DefaultWebSocketServerSession, clientId: String, frameTextReceived: String, payload: GameClientAction
    ) -> Unit
) = webSocket {
    incoming.consumeEach {
        if (it is Frame.Text) {
            val frameTextReceived = it.readText()
            val jsonObject = Json.parseToJsonElement(frameTextReceived).jsonObject
            val payload = when (jsonObject["type"]?.jsonPrimitive?.content) {
                GameClientActionType.SELECT_DICTIONARY -> Json.decodeFromJsonElement<GameClientAction.SelectDictionary>(
                    jsonObject
                )

                GameClientActionType.START_GAME -> Json.decodeFromJsonElement<GameClientAction.StartGame>(
                    jsonObject
                )

                GameClientActionType.PASS_RESULT -> Json.decodeFromJsonElement<GameClientAction.PassResult>(
                    jsonObject
                )

                GameClientActionType.CONNECT -> Json.decodeFromJsonElement<GameClientAction.Connect>(jsonObject)
                else -> null
            }

            call.sessions

            if (payload != null) {
//                handleFrame(this, clientId, frameTextReceived, payload)
            }
        }
    }
}