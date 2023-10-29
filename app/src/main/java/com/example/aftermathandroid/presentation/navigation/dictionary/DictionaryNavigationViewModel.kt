package com.example.aftermathandroid.presentation.navigation.dictionary

import androidx.lifecycle.ViewModel
import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import com.example.aftermathandroid.presentation.navigation.common.back
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DictionaryNavigationViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<NavigationState<DictionaryRoute>>(NavigationState(DictionaryRoute.Menu))
    val state: StateFlow<NavigationState<DictionaryRoute>> = _state

    fun navigateToMenu() {
        _state.value = NavigationState(DictionaryRoute.Menu)
    }

    fun navigateToMyDictionaries() {
        _state.value = NavigationState(DictionaryRoute.My, prevState = _state.value)
    }

    fun navigateToSearch() {
        _state.value = NavigationState(DictionaryRoute.Search, prevState = _state.value)
    }

    fun back() = _state.back()
}