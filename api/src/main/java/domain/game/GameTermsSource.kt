package domain.game

import data.dto.TermDto

sealed class TakeTermResult {
    data class Success(val term: TermDto, val answers: List<TermDto>) : TakeTermResult()
    object TermsEnded : TakeTermResult()
}

class GameTermsSource(val terms: List<TermDto>) {
    private var index = 0

    fun currentTerm(): TermDto {
        return terms[index]
    }

    fun takeTerm(additionalTermsCount: Int): TakeTermResult {
        if (index + additionalTermsCount > terms.size - 1) {
            return TakeTermResult.TermsEnded
        }
        val term = terms[index]

        val answers = mutableListOf(term)

        val shuffledTerms = terms.shuffled()

        var answersIndex = 0

        for (i in 1 until additionalTermsCount) {
            var termToAdd = shuffledTerms[answersIndex]
            while (termToAdd.id == term.id) {
                termToAdd = shuffledTerms[answersIndex]
                answersIndex++
            }
            answers.add(termToAdd)
        }

        index += 1

        return TakeTermResult.Success(term, answers.shuffled())
    }
}