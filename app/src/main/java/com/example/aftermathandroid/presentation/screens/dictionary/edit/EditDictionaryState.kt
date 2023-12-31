package com.example.aftermathandroid.presentation.screens.dictionary.edit

import data.dto.TermDto

data class EditDictionaryState(
    val loading: Boolean = false,
    val name: String = "",
    val description: String = "",
    val terms: List<TermDto> = emptyList(),
    val editTermDialogState: EditTermDialogState? = null,
    val showAddTermDialog: Boolean = false,
    val canEdit: Boolean = false,
    val dictionaryId: String,
    val isFavourite: Boolean = false,
)

data class EditTermDialogState(
    val term: TermDto
)