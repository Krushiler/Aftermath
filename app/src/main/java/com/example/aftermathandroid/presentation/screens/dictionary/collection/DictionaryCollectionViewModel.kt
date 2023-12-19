package com.example.aftermathandroid.presentation.screens.dictionary.collection

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.emitter.DictionarySelectResultEmitter
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import com.example.aftermathandroid.presentation.common.model.PagedList
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.DictionaryInfoDto
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryCollectionViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor,
    private val dictionarySelectResultEmitter: DictionarySelectResultEmitter,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    companion object {
        private const val PAGE_SIZE = 20
    }

    private val _stateFlow = MutableStateFlow(
        DictionaryCollectionState(
            collectionId = savedStateHandle.get<String>("collectionId") ?: "",
        )
    )
    val stateFlow: StateFlow<DictionaryCollectionState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    init {
        loadMore(refresh = true)
    }

    fun selectDictionary(dictionary: DictionaryInfoDto) {
        dictionarySelectResultEmitter.emit(dictionary)
    }

    fun loadMore(refresh: Boolean = false) = viewModelScope.launch {
        try {
            _stateFlow.update { it.copy(isLoading = true) }

            if (refresh) {
                val collectionInfo = dictionaryInteractor.getCollectionInfo(_stateFlow.value.collectionId)
                _stateFlow.update { it.copy(collectionName = collectionInfo.name) }
            }

            val prevState =
                if (refresh) _stateFlow.value.copy(
                    dictionaries = PagedList(),
                    isRefreshing = true,
                ) else _stateFlow.value

            val pagedDictionaries = dictionaryInteractor.getDictionaries(
                limit = PAGE_SIZE,
                offset = prevState.dictionaries.offset,
                collectionId = _stateFlow.value.collectionId
            )

            _stateFlow.update {
                prevState.copy(
                    dictionaries = it.dictionaries.copy(
                        hasNext = pagedDictionaries.hasNext,
                        offset = pagedDictionaries.offset + PAGE_SIZE,
                        items = prevState.dictionaries.items + pagedDictionaries.items,
                        total = pagedDictionaries.total
                    )
                )
            }
        } catch (e: Exception) {
            _errorFlow.emit(ErrorModel.fromException(e))
        }
        _stateFlow.update { it.copy(isLoading = false, isRefreshing = false) }
    }
}