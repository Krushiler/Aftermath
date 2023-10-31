package com.example.aftermathandroid.presentation.screens.dictionary.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDictionaryViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(CreateDictionaryState())
    val stateFlow: StateFlow<CreateDictionaryState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    private val _completedFlow = MutableSharedFlow<CreatingCompletedData>()
    val completedFlow: SharedFlow<CreatingCompletedData> = _completedFlow

    fun createDictionary() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }
            try {
                val dictionary = dictionaryInteractor.createDictionary(
                    name = _stateFlow.value.name, description = _stateFlow.value.description
                )
                _completedFlow.emit(CreatingCompletedData(dictionary))
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { CreateDictionaryState() }
        }
    }

    fun nameChanged(name: String) {
        _stateFlow.update { it.copy(name = name) }
    }

    fun descriptionChanged(description: String) {
        _stateFlow.update { it.copy(description = description) }
    }
}