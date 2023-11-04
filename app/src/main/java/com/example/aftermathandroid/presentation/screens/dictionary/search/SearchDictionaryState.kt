package com.example.aftermathandroid.presentation.screens.dictionary.search

import com.example.aftermathandroid.presentation.common.model.PagedList
import data.dto.DictionaryInfoDto

data class SearchDictionaryState(
    val loading: Boolean = false,
    val dictionaries: PagedList<DictionaryInfoDto> = PagedList(),
    val query: String = "",
)
