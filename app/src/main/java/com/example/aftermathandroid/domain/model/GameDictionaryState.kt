package com.example.aftermathandroid.domain.model

import data.dto.DictionaryInfoDto
import data.dto.QuestionDto

data class GameDictionaryState(
    val dictionary: DictionaryInfoDto,
    val termCount: Int,
    val questions: List<QuestionDto>,
)