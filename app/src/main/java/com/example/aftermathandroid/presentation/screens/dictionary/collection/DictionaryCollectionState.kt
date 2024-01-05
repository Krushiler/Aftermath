package com.example.aftermathandroid.presentation.screens.dictionary.collection

import com.example.aftermathandroid.presentation.common.model.PagedList
import data.dto.DictionaryInfoDto

data class DictionaryCollectionState(
    val isLoading: Boolean = false,
    val dictionaries: PagedList<DictionaryInfoDto> = PagedList(),
    val isRefreshing: Boolean = false,
    val collectionName: String = "",
    val collectionId: String?,
    val isFavourite: Boolean = false,
)