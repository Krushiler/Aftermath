package com.example.aftermathandroid.presentation.navigation.home

sealed class HomeRoute(val path: String) {
    object Game : HomeRoute(path = "game")
    object Dictionaries : HomeRoute(path = "dictionaries")
}
