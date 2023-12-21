package data.dto

data class GameSummaryDto(
    val score: Int,
    val timeSeconds: Int,
    val questions: List<QuestionSummaryDto>
)
