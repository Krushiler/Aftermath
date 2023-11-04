package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

import com.example.aftermathandroid.presentation.common.model.PagedList
import data.dto.DictionaryInfoDto

data class MyDictionariesState(
    val isLoading: Boolean = false,
    val dictionaries: PagedList<DictionaryInfoDto> = PagedList(),
    val showCreateDictionary: Boolean = false,
    val isRefreshing: Boolean = false,
)