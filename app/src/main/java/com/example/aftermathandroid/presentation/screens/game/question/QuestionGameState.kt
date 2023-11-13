package com.example.aftermathandroid.presentation.screens.game.question

import data.dto.QuestionItemDto

data class QuestionGameState(
    val questionText: String = "",
    val answers: List<AnswerUiModel> = emptyList(),
    val timeSeconds: Int = 0,
    val score: Int = 0
)

data class AnswerUiModel(
    val questionItem: QuestionItemDto,
    val answerUiState: AnswerUiState
)

enum class AnswerUiState {
    Correct,
    Incorrect,
    NotAnswered
}