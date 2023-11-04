package com.example.aftermathandroid.presentation.screens.dictionary.search

import androidx.lifecycle.ViewModel
import com.example.aftermathandroid.domain.interactor.DictionaryInteractor
import com.example.aftermathandroid.presentation.common.model.ErrorModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchDictionaryViewModel @Inject constructor(
    private val dictionaryInteractor: DictionaryInteractor,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(SearchDictionaryState())
    val stateFlow: StateFlow<SearchDictionaryState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

}