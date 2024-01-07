package domain.model

import data.dto.DictionaryDto
import kotlinx.serialization.Serializable

@Serializable
data class GameInitParams(
    val dictionaryDto: DictionaryDto,
    val questionsCount: Int = 10,
    val answersCount: Int = 3,
)