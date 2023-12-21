package com.example.aftermathandroid.domain.model

import data.dto.GameSummaryDto
import data.dto.QuestionDto
import data.dto.QuestionItemDto

sealed class LocalGameState {
    data class Question(
        val question: QuestionDto,
        val answeredQuestion: QuestionItemDto?,
        val correctAnswer: QuestionItemDto?,
    ) : LocalGameState()

    data class WaitingForStart(val secondsLeft: Int) : LocalGameState()

    data class GameOver(val summary: GameSummaryDto) : LocalGameState()
}
