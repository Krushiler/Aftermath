package com.example.aftermathandroid.domain.interactor

import com.example.aftermathandroid.data.repository.DictionaryRepository
import com.example.aftermathandroid.data.repository.GameConnectionRepository
import com.example.aftermathandroid.data.repository.LobbyListRepository
import com.example.aftermathandroid.data.repository.LocalGameRepository
import com.example.aftermathandroid.domain.model.GameDictionaryState
import com.example.aftermathandroid.domain.model.LocalGameState
import data.dto.GameSummaryDto
import data.dto.QuestionItemDto
import data.dto.UserDto
import domain.model.GameInitParams
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameInteractor @Inject constructor(
    private val localGameRepository: LocalGameRepository,
    private val dictionaryRepository: DictionaryRepository,
    private val gameConnectionRepository: GameConnectionRepository,
    private val lobbyListRepository: LobbyListRepository
) {
    suspend fun connectToLobby(lobbyId: String) {
        gameConnectionRepository.closeConnection()
        gameConnectionRepository.createConnection(lobbyId)
    }

    suspend fun createLobby() {
        val lobby = lobbyListRepository.createLobby()
        connectToLobby(lobby.id)
    }

    val onlineGameStartedFlow: StateFlow<Boolean> get() = gameConnectionRepository.gameStartedFlow

    val onlineResultsFlow: StateFlow<Map<String, GameSummaryDto>> get() = gameConnectionRepository.resultsFlow

    val onlinePlayersFlow: StateFlow<List<UserDto>> get() = gameConnectionRepository.playersFlow

    val onlineDictionaryFlow: StateFlow<GameDictionaryState?> get() = gameConnectionRepository.dictionaryFlow

    suspend fun startOnlineGame(dictionaryState: GameDictionaryState) {
        val gameInitParams = GameInitParams(
            questionsCount = dictionaryState.questions.size,
            answersCount = dictionaryState.termCount,
            questions = dictionaryState.questions,
        )
        localGameRepository.startGame(gameInitParams)
    }

    suspend fun startLocalGame(
        dictionaryId: String,
        questionsCount: Int,
    ) {
        val dictionary = dictionaryRepository.getDictionary(dictionaryId)
        val gameInitParams = GameInitParams(dictionary, questionsCount)
        localGameRepository.startGame(gameInitParams)
    }

    fun giveAnswer(answer: QuestionItemDto) = localGameRepository.giveAnswer(answer)

    suspend fun getLobbies() = lobbyListRepository.getLobbies()

    val localGameState: StateFlow<LocalGameState> get() = localGameRepository.localGameState

    val timeSecondsFlow: StateFlow<Int> get() = localGameRepository.timeSecondsFlow

    val userScoreState: StateFlow<Int> get() = localGameRepository.userScoreState
}