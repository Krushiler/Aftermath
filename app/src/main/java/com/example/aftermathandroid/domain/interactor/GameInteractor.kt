package com.example.aftermathandroid.domain.interactor

import com.example.aftermathandroid.data.repository.DictionaryRepository
import com.example.aftermathandroid.data.repository.game.LocalGameRepository
import com.example.aftermathandroid.domain.model.GameInitParams
import com.example.aftermathandroid.domain.model.LocalGameState
import data.dto.QuestionItemDto
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameInteractor @Inject constructor(
    private val localGameRepository: LocalGameRepository,
    private val dictionaryRepository: DictionaryRepository,
) {
    suspend fun startLocalGame(
        dictionaryId: String,
        questionsCount: Int,
    ) {
        val dictionary = dictionaryRepository.getDictionary(dictionaryId)
        val gameInitParams = GameInitParams(dictionary, questionsCount)
        localGameRepository.startGame(gameInitParams)
    }

    fun giveAnswer(answer: QuestionItemDto) = localGameRepository.giveAnswer(answer)

    val localGameState: StateFlow<LocalGameState> get() = localGameRepository.localGameState

    val timeSecondsFlow: StateFlow<Int> get() = localGameRepository.timeSecondsFlow

    val userScoreState: StateFlow<Int> get() = localGameRepository.userScoreState
}