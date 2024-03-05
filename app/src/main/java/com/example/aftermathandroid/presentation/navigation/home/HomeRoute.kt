package com.example.aftermathandroid.presentation.navigation.home

import com.example.aftermathandroid.presentation.navigation.common.NavigationRoute

sealed class HomeRoute(val path: String) : NavigationRoute() {
    override fun path() = path

    object Game : HomeRoute(path = "game")
    object Dictionaries : HomeRoute(path = "dictionaries")
    object Lobbies : HomeRoute(path = "lobbies")
}
