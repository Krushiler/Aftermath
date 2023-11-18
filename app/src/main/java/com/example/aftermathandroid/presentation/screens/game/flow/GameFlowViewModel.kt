package com.example.aftermathandroid.presentation.screens.game.flow

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
class GameFlowViewModel @Inject constructor(
    private val gameInteractor: GameInteractor,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(GameFlowState())
    val stateFlow: StateFlow<GameFlowState> = _stateFlow

    init {
        viewModelScope.launch {
            gameInteractor.localGameState.collect { gameState ->
                _stateFlow.update {
                    it.copy(
                        gameScreen = when (gameState) {
                            is LocalGameState.Question -> GameScreen.GAME
                            is LocalGameState.WaitingForStart -> GameScreen.COUNTDOWN
                            is LocalGameState.GameOver -> GameScreen.RESULT
                        }
                    )
                }
            }
        }
    }
}