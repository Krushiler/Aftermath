package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

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
class MyDictionariesViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val _stateFlow = MutableStateFlow(MyDictionariesScreenState())
    val stateFlow: StateFlow<MyDictionariesScreenState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    fun createDictionary() {
        viewModelScope.launch {}
    }

    init {
        loadMore()
    }

    fun loadMore(refresh: Boolean = false) = viewModelScope.launch {
        try {
            _stateFlow.update { it.copy(isLoading = true) }
            val pagedDictionaries = dictionaryInteractor.getMyDictionaries(
                limit = PAGE_SIZE,
                offset = _stateFlow.value.offset
            )
            _stateFlow.update {
                it.copy(
                    hasNext = pagedDictionaries.hasNext,
                    offset = pagedDictionaries.offset,
                    dictionaries = pagedDictionaries.items
                )
            }
        } catch (e: Exception) {
            _errorFlow.emit(ErrorModel.fromException(e))
        }
        _stateFlow.update { it.copy(isLoading = false) }
    }
}