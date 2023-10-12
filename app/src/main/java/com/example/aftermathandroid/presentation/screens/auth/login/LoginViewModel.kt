package com.example.aftermathandroid.presentation.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.data.repository.AuthRepository
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
class LoginViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
    private val _stateFlow = MutableStateFlow(LoginState())
    val stateFlow: StateFlow<LoginState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    fun login() {
        viewModelScope.launch {
            _stateFlow.update { it.copy(isLoading = true) }
            try {
                authRepository.login(_stateFlow.value.login, _stateFlow.value.password)
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
            _stateFlow.update { it.copy(isLoading = false) }
        }
    }

    fun loginChanged(text: String) {
        _stateFlow.update {
            it.copy(
                login = text
            )
        }
    }

    fun passwordChanged(text: String) {
        _stateFlow.update {
            it.copy(
                password = text
            )
        }
    }
}