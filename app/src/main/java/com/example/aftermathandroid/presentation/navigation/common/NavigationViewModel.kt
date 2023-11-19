package com.example.aftermathandroid.presentation.navigation.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

abstract class NavigationViewModel<Route : NavigationRoute> : ViewModel() {
    private val _state = MutableSharedFlow<NavigationState<Route>>()
    val state: SharedFlow<NavigationState<Route>> get() = _state

    private val _backFlow = MutableSharedFlow<Unit>()
    val backFlow: SharedFlow<Unit> get() = _backFlow

    protected fun emit(state: NavigationState<Route>) {
        viewModelScope.launch {
            _state.emit(state)
        }
    }

    fun back() {
        viewModelScope.launch {
            _backFlow.emit(Unit)
        }
    }
}