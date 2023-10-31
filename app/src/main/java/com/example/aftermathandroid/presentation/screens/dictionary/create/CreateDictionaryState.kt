package com.example.aftermathandroid.presentation.screens.dictionary.create

import data.dto.DictionaryDto

data class CreateDictionaryState(
    val loading: Boolean = false,
    val name: String = "",
    val description: String = "",
)

data class CreatingCompletedData(
    val dictionary: DictionaryDto
)