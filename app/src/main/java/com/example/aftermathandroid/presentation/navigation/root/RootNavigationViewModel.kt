package com.example.aftermathandroid.presentation.navigation.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.AuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.AuthDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootNavigationViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : ViewModel() {
    private val _authState = MutableStateFlow<AuthDto?>(null)
    val authState: StateFlow<AuthDto?> get() = _authState

    init {
        viewModelScope.launch {
            authInteractor.watchAuthState().collect {
                _authState.emit(it)
            }
        }
    }
}