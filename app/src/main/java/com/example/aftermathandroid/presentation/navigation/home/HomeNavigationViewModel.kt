package com.example.aftermathandroid.presentation.navigation.home

import com.example.aftermathandroid.presentation.navigation.common.NavigationState
import com.example.aftermathandroid.presentation.navigation.common.NavigationViewModel
import com.example.aftermathandroid.presentation.navigation.common.RouteType
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeNavigationViewModel @Inject constructor() : NavigationViewModel<HomeRoute>() {
    fun navigateToFeed() {
        emit(NavigationState(HomeRoute.Game, routeType = RouteType.Switch))
    }

    fun navigateToDictionaries() {
        emit(NavigationState(HomeRoute.Dictionaries, routeType = RouteType.Switch))
    }
}
