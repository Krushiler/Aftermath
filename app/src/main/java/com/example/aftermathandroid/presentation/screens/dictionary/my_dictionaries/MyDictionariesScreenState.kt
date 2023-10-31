package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

import data.dto.DictionaryInfoDto

data class MyDictionariesScreenState(
    val isLoading: Boolean = false,
    val dictionaries: List<DictionaryInfoDto> = emptyList(),
    val offset: Int = 0,
    val hasNext: Boolean = true,
    val showCreateDictionary: Boolean = false,
    val isRefreshing: Boolean = false,
)