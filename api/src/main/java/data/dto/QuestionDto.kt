package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionItemDto(
    val termId: String,
    val text: String,
)

@Serializable
data class QuestionDto(
    val term: TermDto,
    val question: QuestionItemDto,
    val answers: List<QuestionItemDto>,
)

@Serializable
data class QuestionSummaryDto(
    val question: QuestionDto,
    val selectedAnswer: QuestionItemDto?,
) {
    val isCorrect = selectedAnswer?.termId == question.term.id
}