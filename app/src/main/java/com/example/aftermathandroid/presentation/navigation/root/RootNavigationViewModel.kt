package com.example.aftermathandroid.presentation.navigation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.data.repository.AuthRepository
import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavigationViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow<NavigationState<RootRoute>>(NavigationState(RootRoute.Home))
    val state: StateFlow<NavigationState<RootRoute>> = _state

    init {
        viewModelScope.launch {
            authRepository.watchAuthState().collect {
                val isAuthScreen =
                    _state.value.route == RootRoute.Login || _state.value.route == RootRoute.Registration
                if (it == null && !isAuthScreen) {
                    _state.value = NavigationState(RootRoute.Login)
                } else {
                    _state.value = NavigationState(RootRoute.Home)
                }
            }
        }
    }

    fun navigateToHome() {
        _state.value = NavigationState(RootRoute.Home)
    }

    fun navigateToRegister() {
        _state.value = NavigationState(RootRoute.Registration, prevState = _state.value)
    }

    fun navigateToLogin() {
        _state.value = NavigationState(RootRoute.Login)
    }

    fun back() {
        _state.value.prevState?.let {
            _state.value = it
        }
    }
}