package com.example.aftermathandroid.domain.model

import data.dto.DictionaryDto

data class GameInitParams(
    val dictionaryDto: DictionaryDto,
    val questionsCount: Int = 10,
    val answersCount: Int = 3,
)