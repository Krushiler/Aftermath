package com.example.aftermathandroid.presentation.navigation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.AuthInteractor
import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import com.example.aftermathandroid.presentation.navigation.common.back
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeNavigationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : ViewModel() {
    private val _state = MutableStateFlow<NavigationState<HomeRoute>>(NavigationState(HomeRoute.Game))
    val state: StateFlow<NavigationState<HomeRoute>> = _state

    fun navigateToFeed() {
        _state.value = NavigationState(HomeRoute.Game, prevState = state.value)
    }

    fun navigateToDictionaries() {
        _state.value = NavigationState(HomeRoute.Dictionaries, prevState = state.value)
    }

    fun logout() {
        viewModelScope.launch { authInteractor.logout() }
    }

    fun back() = _state.back()
}
