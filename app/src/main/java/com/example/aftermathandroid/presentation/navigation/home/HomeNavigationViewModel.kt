package com.example.aftermathandroid.presentation.navigation.home

import androidx.lifecycle.ViewModel
import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeNavigationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<NavigationState<HomeRoute>>(NavigationState(HomeRoute.Feed))
    val state: StateFlow<NavigationState<HomeRoute>> = _state

    fun navigateToFeed() {
        _state.value = NavigationState(HomeRoute.Feed)
    }

    fun navigateToMessages() {
        _state.value = NavigationState(HomeRoute.Messages)
    }

    fun navigateToSettings() {
        _state.value = NavigationState(HomeRoute.Settings)
    }

    fun back() {
        _state.value.prevState?.let {
            _state.value = it
        }
    }
}
