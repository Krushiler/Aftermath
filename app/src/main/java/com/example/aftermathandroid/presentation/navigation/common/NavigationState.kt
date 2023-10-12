package com.example.aftermathandroid.presentation.navigation.common

data class NavigationState<R>(
    val route: R,
    val prevState: NavigationState<R>? = null
)
