package com.example.aftermathandroid.presentation.navigation.common

import kotlinx.coroutines.flow.MutableStateFlow

data class NavigationState<R : NavigationRoute>(
    val route: R,
    val prevState: NavigationState<R>? = null,
    val routeType: RouteType = RouteType.Normal
) {
    fun path(): String = route.path()
}

sealed class RouteType {
    object Normal : RouteType()
    object Root : RouteType()
    object Switch : RouteType()
}

fun <T : NavigationRoute> MutableStateFlow<NavigationState<T>>.back(): Boolean = value.prevState?.let {
    value = it
    return true
} ?: false
