package com.example.aftermathandroid.presentation.screens.game.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.GameInteractor
import com.example.aftermathandroid.domain.model.LocalGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultGameViewModel @Inject constructor(
    private val gameInteractor: GameInteractor
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(ResultGameState())
    val stateFlow: StateFlow<ResultGameState> = _stateFlow

    init {
        viewModelScope.launch {
            gameInteractor.localGameState.collect { gameState ->
                if (gameState is LocalGameState.GameOver) {
                    _stateFlow.update {
                        it.copy(
                            time = gameState.summary.timeSeconds,
                            score = gameState.summary.score,
                            questions = gameState.summary.questions,
                            totalQuestions = gameState.summary.questions.size
                        )
                    }
                }
            }
        }
    }

    fun switchSummary() {
        _stateFlow.update { it.copy(showSummary = !it.showSummary) }
    }
}