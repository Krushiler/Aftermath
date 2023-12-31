package com.example.aftermathandroid.presentation.screens.game.countdown

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
class CountdownGameViewModel @Inject constructor(
    private val gameInteractor: GameInteractor
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(CountdownGameState())
    val stateFlow: StateFlow<CountdownGameState> = _stateFlow

    init {
        viewModelScope.launch {
            gameInteractor.localGameState.collect { gameState ->
                if (gameState is LocalGameState.WaitingForStart) {
                    _stateFlow.update { it.copy(timeSeconds = gameState.secondsLeft) }
                }
            }
        }
    }
}