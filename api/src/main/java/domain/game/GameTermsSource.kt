package domain.game

import data.dto.QuestionDto
import data.dto.QuestionItemDto
import data.dto.TermDto

sealed class TakeTermResult {
    data class Success(val question: QuestionDto) : TakeTermResult()
    object TermsEnded : TakeTermResult()
}

class GameTermsSource(
    val terms: List<TermDto> = emptyList(),
    private val termsCount: Int,
    val questions: List<QuestionDto> = emptyList()
) {
    private var index = 0

    fun takeTerm(additionalTermsCount: Int): TakeTermResult {
        if (questions.isNotEmpty() && index < questions.size) {
            return TakeTermResult.Success(questions[index])
        }

        if (index + additionalTermsCount > terms.size || index >= termsCount) {
            return TakeTermResult.TermsEnded
        }
        val term = terms[index]

        val answers = mutableListOf(term)

        val shuffledTerms = terms.shuffled().toMutableList()
        shuffledTerms.remove(term)

        for (i in 1 until additionalTermsCount) {
            val termToAdd = shuffledTerms[i]
            answers.add(termToAdd)
        }

        index += 1

        return TakeTermResult.Success(
            QuestionDto(
                term,
                QuestionItemDto(term.id, term.description),
                answers.shuffled().map { QuestionItemDto(it.id, it.name) }
            )
        )
    }
}