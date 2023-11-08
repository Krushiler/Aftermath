package com.example.aftermathandroid.domain.repository

import com.example.aftermathandroid.domain.model.GameInitParams
import com.example.aftermathandroid.domain.model.LocalGameState
import data.dto.QuestionItemDto
import kotlinx.coroutines.flow.StateFlow

interface GameRepository {
    val localGameState: StateFlow<LocalGameState>

    val timeSecondsFlow: StateFlow<Int>

    fun giveAnswer(answer: QuestionItemDto)

    fun startGame(gameInitParams: GameInitParams)
}