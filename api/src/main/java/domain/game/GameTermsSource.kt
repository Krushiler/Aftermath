package domain.game

import data.dto.TermDto

sealed class TakeTermResult {
    data class Success(val term: TermDto, val answers: List<TermDto>) : TakeTermResult()
    object TermsEnded : TakeTermResult()
}

class GameTermsSource(val terms: List<TermDto>, private val termsCount: Int) {
    private var index = 0

    fun takeTerm(additionalTermsCount: Int): TakeTermResult {
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

        return TakeTermResult.Success(term, answers.shuffled())
    }
}