package com.example.aftermathandroid.presentation.screens.game.result

import data.dto.QuestionSummaryDto

data class ResultGameState(
    val time: Int = 0,
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val questions: List<QuestionSummaryDto> = emptyList(),
    val showSummary: Boolean = false,
)