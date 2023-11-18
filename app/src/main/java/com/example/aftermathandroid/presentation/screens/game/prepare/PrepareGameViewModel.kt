package com.example.aftermathandroid.presentation.screens.game.prepare

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.GameInteractor
import com.example.aftermathandroid.presentation.common.emitter.DictionarySelectResultEmitter
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
class PrepareGameViewModel @Inject constructor(
    private val dictionarySelectResultEmitter: DictionarySelectResultEmitter,
    private val gameInteractor: GameInteractor,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(PrepareGameState())
    val stateFlow: StateFlow<PrepareGameState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    private val _gameStartedFlow = MutableSharedFlow<Unit>()
    val gameStartedFlow: SharedFlow<Unit> = _gameStartedFlow

    init {
        viewModelScope.launch {
            dictionarySelectResultEmitter.subscribe { dictionary ->
                _stateFlow.update { it.copy(dictionary = dictionary) }
            }
        }
    }

    fun changeQuestionCount(questionCountValue: String) {
        _stateFlow.update { it.copy(questionCountValue = questionCountValue) }
    }

    fun startGame() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(loading = true) }
            try {
                gameInteractor.startLocalGame(
                    questionsCount = stateFlow.value.questionCountValue.toIntOrNull() ?: 10,
                    dictionaryId = stateFlow.value.dictionary?.id ?: ""
                )
                _gameStartedFlow.emit(Unit)
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(loading = false) }
        }
    }
}