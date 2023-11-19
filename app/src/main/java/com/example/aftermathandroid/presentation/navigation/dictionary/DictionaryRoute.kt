package com.example.aftermathandroid.presentation.navigation.dictionary

import com.example.aftermathandroid.presentation.navigation.common.NavigationRoute

sealed class DictionaryRoute(val path: String) : NavigationRoute() {
    override fun path() = path

    object Menu : DictionaryRoute(path = "menu")
    object My : DictionaryRoute(path = "my")
    object Search : DictionaryRoute(path = "search")
}