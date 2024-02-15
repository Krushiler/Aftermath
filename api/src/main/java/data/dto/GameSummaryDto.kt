package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class GameSummaryDto(
    val score: Int,
    val timeSeconds: Int,
    val questions: List<QuestionSummaryDto>
)
