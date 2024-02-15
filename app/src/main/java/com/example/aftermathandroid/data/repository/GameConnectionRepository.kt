package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.domain.model.GameDictionaryState
import data.dto.GameClientAction
import data.dto.GameServerAction
import data.dto.GameServerActionType
import data.dto.GameSummaryDto
import data.dto.UserDto
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class GameConnectionRepository(private val client: HttpClient) {

    private val playersFlow = MutableStateFlow<List<UserDto>>(emptyList())
    private val gameStartedFlow = MutableSharedFlow<Boolean>()
    private val resultsFlow = MutableStateFlow<Map<String, GameSummaryDto>>(emptyMap())
    private val dictionaryFlow = MutableStateFlow<GameDictionaryState?>(null)

    private val actionsFlow = MutableSharedFlow<GameClientAction>()

    suspend fun joinLobby(lobbyId: String) {
        actionsFlow.emit(GameClientAction.JoinLobby(lobbyId))
    }

    suspend fun selectDictionary(dictionaryId: String, termCount: Int) {
        actionsFlow.emit(GameClientAction.SelectDictionary(dictionaryId, termCount))
    }

    suspend fun passResult(summary: GameSummaryDto) {
        actionsFlow.emit(GameClientAction.PassResult(summary))
    }

    suspend fun leaveLobby() {
        actionsFlow.emit(GameClientAction.LeaveLobby)
    }

    suspend fun startGame() {
        actionsFlow.emit(GameClientAction.StartGame)
    }

    suspend fun connect(userId: String) {
        actionsFlow.emit(GameClientAction.Connect(userId))
    }

    suspend fun createConnection() = client.webSocket("/game") {
        launch { lobbyOutput() }
        launch { lobbyInput() }
    }

    private suspend fun DefaultClientWebSocketSession.lobbyInput() {
        actionsFlow.collect {
            sendSerialized(it)
        }
    }

    private suspend fun DefaultClientWebSocketSession.lobbyOutput() {
        for (message in incoming) {
            message as? Frame.Text ?: continue
            val frameTextReceived = message.readText()
            val jsonObject = Json.parseToJsonElement(frameTextReceived).jsonObject
            val payload = when (jsonObject["type"]?.jsonPrimitive?.content) {
                GameServerActionType.USERS_UPDATED -> Json.decodeFromJsonElement<GameServerAction.UsersUpdated>(
                    jsonObject
                )

                GameServerActionType.GAME_STARTED -> Json.decodeFromJsonElement<GameServerAction.GameStarted>(
                    jsonObject
                )

                GameServerActionType.RESULTS_CHANGED -> Json.decodeFromJsonElement<GameServerAction.ResultsChanged>(
                    jsonObject
                )

                GameServerActionType.DICTIONARY_SELECTED -> Json.decodeFromJsonElement<GameServerAction.DictionarySelected>(
                    jsonObject
                )

                else -> null
            } ?: continue

            when (payload) {
                is GameServerAction.DictionarySelected -> dictionaryFlow.emit(
                    GameDictionaryState(
                        dictionary = payload.dictionary,
                        termCount = payload.termCount,
                        questions = payload.questions
                    )
                )

                is GameServerAction.GameStarted -> gameStartedFlow.emit(true)
                is GameServerAction.ResultsChanged -> resultsFlow.emit(payload.summaries)
                is GameServerAction.UsersUpdated -> playersFlow.emit(payload.users)
            }
        }
    }
}