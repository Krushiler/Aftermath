package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import com.example.aftermathandroid.presentation.common.model.PagedList
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
        private const val PAGE_SIZE = 20
    }

    private val _stateFlow = MutableStateFlow(MyDictionariesState())
    val stateFlow: StateFlow<MyDictionariesState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    fun createDictionary() {
        _stateFlow.update { it.copy(showCreateDictionary = true) }
    }

    fun completedCreateDictionary(created: Boolean) {
        _stateFlow.update { it.copy(showCreateDictionary = false) }
        if (created) loadMore(refresh = true)
    }

    init {
        loadMore()
    }

    fun loadMore(refresh: Boolean = false) = viewModelScope.launch {
        try {
            if (refresh) _stateFlow.update {
                it.copy(
                    dictionaries = PagedList(),
                    isRefreshing = true,
                )
            }
            _stateFlow.update { it.copy(isLoading = true) }
            val pagedDictionaries = dictionaryInteractor.getMyDictionaries(
                limit = PAGE_SIZE, offset = _stateFlow.value.dictionaries.offset
            )
            _stateFlow.update {
                it.copy(
                    dictionaries = it.dictionaries.copy(
                        hasNext = pagedDictionaries.hasNext,
                        offset = pagedDictionaries.offset + PAGE_SIZE,
                        items = _stateFlow.value.dictionaries.items + pagedDictionaries.items
                    )
                )
            }
        } catch (e: Exception) {
            _errorFlow.emit(ErrorModel.fromException(e))
        }
        _stateFlow.update { it.copy(isLoading = false, isRefreshing = false) }
    }
}