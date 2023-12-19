package com.example.aftermathandroid.presentation.navigation.dictionary

import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import com.example.aftermathandroid.presentation.navigation.common.NavigationViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DictionaryNavigationViewModel @Inject constructor() : NavigationViewModel<DictionaryRoute>() {
    fun navigateToMyDictionaries() {
        emit(NavigationState(DictionaryRoute.My))
    }

    fun navigateToSearch() {
        emit(NavigationState(DictionaryRoute.Search))
    }

    fun navigateToCollection(collectionId: String) {
        emit(NavigationState(DictionaryRoute.Collection(collectionId)))
    }
}