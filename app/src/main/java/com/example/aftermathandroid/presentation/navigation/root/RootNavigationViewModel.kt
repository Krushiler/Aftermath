package com.example.aftermathandroid.presentation.navigation.root

import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.AuthInteractor
import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import com.example.aftermathandroid.presentation.navigation.common.NavigationViewModel
import com.example.aftermathandroid.presentation.navigation.common.RouteType
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.AuthDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavigationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : NavigationViewModel<RootRoute>() {
    private val _authState = MutableStateFlow<AuthDto?>(null)
    val authState: StateFlow<AuthDto?> get() = _authState

    init {
        viewModelScope.launch {
            authInteractor.watchAuthState().collect {
                _authState.emit(it)
            }
        }
    }

    fun navigateToProfile() {
        emit(NavigationState(RootRoute.Profile))
    }

    fun navigateToRegister() {
        emit(NavigationState(RootRoute.Registration))
    }

    fun navigateToLogin() {
        emit(NavigationState(RootRoute.Login, routeType = RouteType.Root))
    }

    fun navigateToEditDictionary(dictionaryId: String) {
        emit(NavigationState(RootRoute.EditDictionary(dictionaryId)))
    }

    fun navigateToSelectDictionary() {
        emit(NavigationState(RootRoute.SelectDictionary))
    }

    fun navigateToGame() {
        emit(NavigationState(RootRoute.Game))
    }

    fun logout() {
        viewModelScope.launch {
            authInteractor.logout()
        }
    }
}