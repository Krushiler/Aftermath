package com.example.aftermathandroid.presentation.screens.dictionary.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.TermDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import util.generateUUID
import javax.inject.Inject

@HiltViewModel
class EditDictionaryViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _stateFlow =
        MutableStateFlow(EditDictionaryState(dictionaryId = savedStateHandle.get<String>("dictionaryId") ?: ""))
    val stateFlow: StateFlow<EditDictionaryState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    init {
        viewModelScope.launch {
            try {
                _stateFlow.update { it.copy(loading = true) }
                val dictionary = dictionaryInteractor.getDictionary(_stateFlow.value.dictionaryId)
                _stateFlow.update {
                    it.copy(
                        name = dictionary.name,
                        description = dictionary.description,
                        terms = dictionary.terms,
                        canEdit = dictionary.canEdit
                    )
                }
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(loading = false) }
        }
    }

    fun save() {
        viewModelScope.launch {
            try {
                _stateFlow.update { it.copy(loading = true) }
                dictionaryInteractor.updateDictionary(
                    _stateFlow.value.dictionaryId,
                    stateFlow.value.name,
                    stateFlow.value.description,
                    stateFlow.value.terms
                )
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(loading = false) }
        }
    }

    fun nameChanged(name: String) {
        _stateFlow.update { state ->
            state.copy(name = name)
        }
    }

    fun dictionaryDescriptionChanged(description: String) {
        _stateFlow.update { state ->
            state.copy(description = description)
        }
    }

    fun termChanged(term: TermDto) {
        _stateFlow.update { state ->
            state.copy(terms = state.terms.map {
                if (it.id == term.id) {
                    return@map it.copy(
                        name = term.name,
                        description = term.description
                    )
                }
                return@map it
            })
        }
    }

    fun termDeleted(term: TermDto) {
        _stateFlow.update { state ->
            state.copy(terms = state.terms.filter { it.id != term.id })
        }
    }

    fun addTermPressed() {
        _stateFlow.update { state -> state.copy(showAddTermDialog = true) }
    }

    fun addTerm(name: String, description: String) {
        _stateFlow.update { state ->
            state.copy(terms = state.terms + TermDto(generateUUID(), name, description))
        }
    }

    fun closeAddTermDialog() {
        _stateFlow.update { it.copy(showAddTermDialog = false) }
    }

    fun closeEditTermDialog() {
        _stateFlow.update { it.copy(editTermDialogState = null) }
    }

    fun editTermPressed(term: TermDto) {
        _stateFlow.update { it.copy(editTermDialogState = EditTermDialogState(term)) }
    }

    fun toggleFavourite() {
        val prevState = _stateFlow.value
        _stateFlow.update { it.copy(isFavourite = !it.isFavourite) }
        viewModelScope.launch {
            try {
                dictionaryInteractor.changeFavourite(
                    _stateFlow.value.dictionaryId,
                    _stateFlow.value.isFavourite
                )
            } catch (e: Exception) {
                _stateFlow.update { prevState }
                _errorFlow.emit(ErrorModel.fromException(e))
            }
        }
    }
}