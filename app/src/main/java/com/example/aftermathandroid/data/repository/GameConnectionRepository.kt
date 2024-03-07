package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.storage.preferences.PreferencesManager
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
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameConnectionRepository @Inject constructor(
    private val client: HttpClient,
    private val preferencesManager: PreferencesManager
) {

    private var socket: DefaultClientWebSocketSession? = null

    private val _playersFlow = MutableStateFlow<List<UserDto>>(emptyList())
    val playersFlow: StateFlow<List<UserDto>> get() = _playersFlow

    private val _gameStartedFlow = MutableStateFlow(false)
    val gameStartedFlow: StateFlow<Boolean> get() = _gameStartedFlow

    private val _resultsFlow = MutableStateFlow<Map<String, GameSummaryDto>>(emptyMap())
    val resultsFlow: StateFlow<Map<String, GameSummaryDto>> get() = _resultsFlow

    private val _dictionaryFlow = MutableStateFlow<GameDictionaryState?>(null)
    val dictionaryFlow: StateFlow<GameDictionaryState?> get() = _dictionaryFlow

    private val actionsFlow = MutableSharedFlow<GameClientAction>()

    suspend fun selectDictionary(dictionaryId: String, termCount: Int) {
        actionsFlow.emit(GameClientAction.SelectDictionary(dictionaryId, termCount))
    }

    suspend fun passResult(summary: GameSummaryDto) {
        actionsFlow.emit(GameClientAction.PassResult(summary))
    }

    suspend fun startGame() {
        actionsFlow.emit(GameClientAction.StartGame)
    }

    private suspend fun connect(token: String) {
        actionsFlow.emit(GameClientAction.Connect(token))
    }

    suspend fun createConnection(lobbyId: String) = client.webSocket("/game/$lobbyId") {
        socket = this
        launch { lobbyOutput(lobbyId) }
        launch { lobbyInput(lobbyId) }
        connect(preferencesManager.token ?: "")
    }

    suspend fun closeConnection() {
        socket?.close()
    }

    private suspend fun DefaultClientWebSocketSession.lobbyInput(lobbyId: String) {
        actionsFlow.collect {
            sendSerialized(it)
        }
    }

    private suspend fun DefaultClientWebSocketSession.lobbyOutput(lobbyId: String) {
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
                is GameServerAction.DictionarySelected -> _dictionaryFlow.emit(
                    GameDictionaryState(
                        dictionary = payload.dictionary,
                        termCount = payload.termCount,
                        questions = payload.questions
                    )
                )

                is GameServerAction.GameStarted -> `_gameStartedFlow`.emit(true)
                is GameServerAction.ResultsChanged -> _resultsFlow.emit(payload.summaries)
                is GameServerAction.UsersUpdated -> _playersFlow.emit(payload.users)
            }
        }
    }
}