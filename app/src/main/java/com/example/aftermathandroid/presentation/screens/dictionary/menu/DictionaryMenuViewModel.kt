package com.example.aftermathandroid.presentation.screens.dictionary.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.AuthInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryMenuViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            authInteractor.logout()
        }
    }
}