package com.example.aftermathandroid.presentation.screens.dictionary.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.emitter.DictionarySelectResultEmitter
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import com.example.aftermathandroid.presentation.common.model.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.DictionaryInfoDto
import domain.model.DictionarySearchData
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchDictionaryViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor,
    private val dictionarySelectResultEmitter: DictionarySelectResultEmitter,
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 20
    }

    private val _stateFlow = MutableStateFlow(SearchDictionaryState())
    val stateFlow: StateFlow<SearchDictionaryState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    private val _queryFlow = MutableStateFlow("")
    private var _searchJob: Job? = null

    init {
        viewModelScope.launch {
            _queryFlow.debounce(500).onEach { searchDictionary() }.collect()
        }
    }

    fun selectDictionary(dictionary: DictionaryInfoDto) {
        dictionarySelectResultEmitter.emit(dictionary)
    }

    fun queryChanged(query: String) {
        _stateFlow.update { it.copy(query = query) }
        _queryFlow.update { query }
    }

    fun searchDictionary(refresh: Boolean = true) {
        _searchJob?.cancel()
        _searchJob = viewModelScope.launch {
            if (_stateFlow.value.query.isEmpty()) return@launch
            _stateFlow.update { it.copy(loading = true) }
            try {
                if (refresh) _stateFlow.update { it.copy(dictionaries = PagedList()) }
                val dictionaries = dictionaryInteractor.getDictionaries(
                    limit = PAGE_SIZE,
                    offset = _stateFlow.value.dictionaries.offset,
                    searchData = DictionarySearchData(query = _stateFlow.value.query.trim())
                )
                _stateFlow.update {
                    it.copy(
                        dictionaries = _stateFlow.value.dictionaries.copy(
                            items = _stateFlow.value.dictionaries.items + dictionaries.items,
                            hasNext = dictionaries.hasNext,
                            offset = dictionaries.offset + PAGE_SIZE,
                            total = dictionaries.total
                        )
                    )
                }
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(loading = false) }
        }
    }
}