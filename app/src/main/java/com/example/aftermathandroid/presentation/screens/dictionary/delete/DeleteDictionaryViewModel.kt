package com.example.aftermathandroid.presentation.screens.dictionary.delete

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
class DeleteDictionaryViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(DeleteDictionaryState())
    val stateFlow: StateFlow<DeleteDictionaryState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    private val _completedFlow = MutableSharedFlow<Unit>()
    val completedFlow: SharedFlow<Unit> = _completedFlow

    fun deleteDictionary(id: String) {
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }
            try {
                dictionaryInteractor.deleteDictionary(id)
                _completedFlow.emit(Unit)
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(loading = false) }
        }
    }
}