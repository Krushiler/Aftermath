package com.example.aftermathandroid.presentation.screens.game.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.GameInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val gameInteractor: GameInteractor
) : ViewModel() {
    init {
        observeGame()
    }

    private val _startGameFlow = MutableSharedFlow<Boolean>()
    val startGameFlow: SharedFlow<Boolean> get() = _startGameFlow

    private val _state = MutableStateFlow(LobbyScreenState())
    val state: StateFlow<LobbyScreenState> get() = _state

    private fun observeGame() {
        viewModelScope.launch {
            gameInteractor.onlineGameStartedFlow.collect {
                _startGameFlow.emit(it)
            }
        }

        viewModelScope.launch {
            gameInteractor.onlinePlayersFlow.collect {
                _state.value = _state.value.copy(
                    players = it
                )
            }
        }

        viewModelScope.launch {
            gameInteractor.onlineDictionaryFlow.collect {
                _state.value = _state.value.copy(
                    dictionaryState = it
                )
            }
        }
    }
}