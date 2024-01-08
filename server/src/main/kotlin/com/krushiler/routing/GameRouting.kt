package com.krushiler.routing

import com.krushiler.domain.interactor.GameInteractor
import data.dto.GameClientAction
import data.dto.GameClientActionType
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.route
import io.ktor.server.websocket.DefaultWebSocketServerSession
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.channels.consumeEach
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.koin.ktor.ext.inject

fun Routing.gameRouting() = route("/game") {
    val gameInteractor: GameInteractor by inject()

    gameWebSocket { socket, userId, _, payload ->
        when (payload) {
            is GameClientAction.SelectDictionary -> {
                if (userId == null) socket.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unauthorized"))
                else gameInteractor.selectDictionary(userId, payload.dictionaryId, payload.termCount)
            }

            is GameClientAction.Connect -> {
                gameInteractor.registerConnection(payload.userId, socket)
            }

            is GameClientAction.JoinLobby -> {
                if (userId == null) socket.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unauthorized"))
                else gameInteractor.connectToLobby(userId, payload.lobbyId)
            }

            is GameClientAction.LeaveLobby -> {
                if (userId == null) socket.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unauthorized"))
                else gameInteractor.leaveLobby(userId)
            }

            is GameClientAction.PassResult -> {
                if (userId == null) socket.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unauthorized"))
                else gameInteractor.passResult(userId, payload.summary)
            }

            is GameClientAction.StartGame -> {
                if (userId == null) socket.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, "Unauthorized"))
                else gameInteractor.startGame(userId)
            }
        }
    }
}

private fun Route.gameWebSocket(
    handleFrame: suspend (
        socket: DefaultWebSocketServerSession, userId: String?, frameTextReceived: String, payload: GameClientAction
    ) -> Unit
) = webSocket {
    var userId: String? = null
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

            if (payload is GameClientAction.Connect) {
                userId = payload.userId
            }

            if (payload != null) {
                handleFrame(this, userId, frameTextReceived, payload)
            }
        }
    }
}