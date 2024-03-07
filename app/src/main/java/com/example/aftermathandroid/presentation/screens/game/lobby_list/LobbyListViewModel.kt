package com.example.aftermathandroid.presentation.screens.game.lobby_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.GameInteractor
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
class LobbyListViewModel @Inject constructor(private val gameInteractor: GameInteractor) : ViewModel() {

    private val _state = MutableStateFlow(LobbyListScreenState())
    val state: StateFlow<LobbyListScreenState> get() = _state

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> get() = _errorFlow

    init {
        loadLobbies()
    }

    private fun loadLobbies() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val lobbies = gameInteractor.getLobbies()
                _state.update { it.copy(lobbies = lobbies) }
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _state.update { it.copy(isLoading = false) }
        }
    }
}