package com.example.aftermathandroid.presentation.navigation.common

import kotlinx.coroutines.flow.MutableStateFlow

data class NavigationState<R>(
    val route: R,
    val prevState: NavigationState<R>? = null
) {
    val canPop: Boolean get() = prevState != null
}

fun <T> MutableStateFlow<NavigationState<T>>.back() = value.prevState?.let { value = it }
