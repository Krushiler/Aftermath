package com.krushiler.domain.interactor

import com.krushiler.data.repository.DictionaryRepository
import com.krushiler.data.repository.GameRepository
import com.krushiler.data.repository.UserRepository
import data.dto.LobbyDto
import io.ktor.websocket.DefaultWebSocketSession

class GameInteractor(
    private val gameRepository: GameRepository,
    private val userRepository: UserRepository,
    private val dictionaryRepository: DictionaryRepository
) {
    suspend fun getLobbies(): List<LobbyDto> = gameRepository.getLobbies()

    suspend fun getLobby(id: String): LobbyDto? = gameRepository.getLobby(id)

    suspend fun leaveLobby(userId: String) = gameRepository.leaveLobby(userId)

    suspend fun getDictionaryInfo(lobbyId: String) = gameRepository.getDictionaryInfo(lobbyId)

    suspend fun getQuestions(lobbyId: String) = gameRepository.getQuestions(lobbyId)

    fun getUsers(lobbyId: String) = gameRepository.getUsers(lobbyId)

    suspend fun createLobby(userId: String, webSocketSession: DefaultWebSocketSession): LobbyDto {
        val user = userRepository.getUserByLogin(userId)
        return gameRepository.createLobby(user, webSocketSession)
    }

    suspend fun registerConnection(userId: String, webSocketSession: DefaultWebSocketSession) =
        gameRepository.registerConnection(userId, webSocketSession)

    suspend fun connectToLobby(userId: String, lobbyId: String) =
        gameRepository.connectToLobby(userId, lobbyId)

    suspend fun startGame(lobbyId: String) = gameRepository.startGame(lobbyId)

    suspend fun selectDictionary(lobbyId: String, dictionaryId: String, termCount: Int) {
        val dictionary = dictionaryRepository.getDictionary(dictionaryId, null) ?: return
        gameRepository.selectDictionary(lobbyId, dictionary, termCount)
    }
}
