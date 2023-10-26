package com.example.aftermathandroid.presentation.screens.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.AuthInteractor
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
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(ProfileState())
    val stateFlow: StateFlow<ProfileState> = _stateFlow

    private val _errorFlow = MutableSharedFlow<ErrorModel>()
    val errorFlow: SharedFlow<ErrorModel> = _errorFlow

    init {
        viewModelScope.launch {
            authInteractor.watchUser().collect { user ->
                _stateFlow.update {
                    it.copy(
                        name = user?.name ?: "",
                        avatar = user?.avatar,
                        initialName = user?.name ?: "",
                        initialAvatar = user?.avatar
                    )
                }
            }
        }
    }

    fun nameChanged(name: String) {
        _stateFlow.update { it.copy(name = name) }
    }

    fun saveChanges() {
        viewModelScope.launch {
            try {
                _stateFlow.update { it.copy(loading = true) }
                authInteractor.updateUser(_stateFlow.value.name, _stateFlow.value.avatar)
                _stateFlow.update { it.copy(loading = false, initialName = it.name, initialAvatar = it.avatar) }
            } catch (e: Exception) {
                _errorFlow.emit(ErrorModel.fromException(e))
            }
        }
    }

    fun logout() {
        viewModelScope.launch { authInteractor.logout() }
    }
}