package com.krushiler.data.repository

import com.krushiler.data.websocket.GameWebsocketConnection
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.GameServerAction
import data.dto.GameSummaryDto
import data.dto.LobbyDto
import data.dto.LobbyStatus
import data.dto.QuestionDto
import data.dto.QuestionItemDto
import data.dto.UserDto
import domain.game.GameTermsSource
import domain.game.TakeTermResult
import io.ktor.server.websocket.DefaultWebSocketServerSession

class GameRepository {
    private val userLobbies = mutableMapOf<String, LobbyDto>()
    private val lobbies = mutableMapOf<String, LobbyDto>()
    private val connections = mutableMapOf<String, GameWebsocketConnection>()

    private val source = mutableMapOf<String, GameTermsSource>()

    fun getLobbies(): List<LobbyDto> = lobbies.values.filter { it.status == LobbyStatus.NOT_STARTED }.toList()

    fun getLobby(id: String): LobbyDto? = lobbies[id]

    fun getUserLobby(userId: String): LobbyDto? = userLobbies[userId]

    suspend fun leaveLobby(userId: String) {
        val lobby = userLobbies[userId]
        if (lobby != null) {
            lobby.players = lobby.players.filter { it.login != userId }.toMutableList()
            userLobbies.remove(userId)
            doForLobbyConnections(lobby) {
                it.sendAction(GameServerAction.UsersUpdated(lobby.players))
            }
            if (lobby !in userLobbies.values) {
                lobbies.remove(lobby.id)
            }
        }
    }

    suspend fun getDictionaryInfo(lobbyId: String): DictionaryInfoDto? {
        val lobby = lobbies[lobbyId] ?: return null
        return lobby.dictionary
    }

    suspend fun getQuestions(lobbyId: String): List<QuestionDto> {
        val lobby = lobbies[lobbyId] ?: return emptyList()
        return lobby.questions
    }

    fun getUsers(lobbyId: String): List<UserDto> = lobbies[lobbyId]?.players ?: emptyList()

    fun createLobby(userDto: UserDto, webSocketSession: DefaultWebSocketServerSession): LobbyDto {
        val id = "lobby-${System.currentTimeMillis()}"
        val lobby = LobbyDto(
            id = id,
            name = "Lobby ${id.substring(6, 9)}",
            status = LobbyStatus.NOT_STARTED,
            host = userDto,
            dictionary = null,
            questions = listOf(),
            players = listOf(userDto)
        )
        lobbies[id] = lobby
        userLobbies[userDto.login] = lobby
        connections[userDto.login] = GameWebsocketConnection(webSocketSession)
        return lobby
    }

    suspend fun registerConnection(userId: String, webSocketSession: DefaultWebSocketServerSession) {
        connections[userId] = GameWebsocketConnection(webSocketSession)
    }

    suspend fun connectToLobby(userId: String, lobbyId: String) {
        val lobby = lobbies[lobbyId] ?: return
        userLobbies[userId] = lobby
        doForLobbyConnections(lobby) {
            it.sendAction(GameServerAction.UsersUpdated(lobby.players))
        }
    }

    suspend fun selectDictionary(id: String, dictionary: DictionaryDto, termsCount: Int) {
        val lobby = lobbies[id] ?: return
        source[id] = GameTermsSource(dictionary.terms, termsCount)
        val questions = mutableListOf<QuestionDto>()
        for (i in 0 until termsCount) {
            val result = source[id]!!.takeTerm(3)
            if (result is TakeTermResult.Success) {
                questions.add(
                    QuestionDto(
                        result.term,
                        QuestionItemDto(result.term.id, result.term.description),
                        result.answers.map { QuestionItemDto(it.id, it.description) }
                    )
                )
            }
        }
        lobby.dictionary = DictionaryInfoDto.fromDictionary(dictionary)
        lobby.questions = questions
        doForLobbyConnections(lobby) {
            it.sendAction(
                GameServerAction.DictionarySelected(
                    lobby.dictionary!!,
                    termsCount,
                    lobby.questions
                )
            )
        }
    }

    suspend fun passResult(userId: String, summary: GameSummaryDto) {
        val lobby = userLobbies[userId] ?: return
        lobby.results[userId] = summary
        doForLobbyConnections(lobby) {
            it.sendAction(GameServerAction.ResultsChanged(lobby.results))
        }
    }

    suspend fun startGame(id: String) {
        val lobby = lobbies[id] ?: return
        lobby.status = LobbyStatus.STARTED
        doForLobbyConnections(lobby) {
            it.sendAction(GameServerAction.GameStarted)
        }
    }

    private fun lobbyConnections(lobby: LobbyDto) = connections.filter { userLobbies[it.key] == lobby }

    private inline fun doForLobbyConnections(lobby: LobbyDto, action: (GameWebsocketConnection) -> Unit) {
        lobbyConnections(lobby).forEach { action(it.value) }
    }
}