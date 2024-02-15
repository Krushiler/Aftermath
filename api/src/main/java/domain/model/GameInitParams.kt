package domain.model

import data.dto.DictionaryDto
import data.dto.QuestionDto
import kotlinx.serialization.Serializable

@Serializable
data class GameInitParams(
    val dictionaryDto: DictionaryDto? = null,
    val questionsCount: Int = 10,
    val answersCount: Int = 3,
    val questions: List<QuestionDto> = emptyList(),
)