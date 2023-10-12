package com.example.aftermathandroid.presentation.navigation.home

sealed class HomeRoute(val path: String) {
    object Feed : HomeRoute(path = "feed")
    object Messages : HomeRoute(path = "messages")
    object Settings : HomeRoute(path = "settings")
}
