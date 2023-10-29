package com.example.aftermathandroid.presentation.navigation.dictionary

sealed class DictionaryRoute(val path: String) {
    object Menu : DictionaryRoute(path = "menu")
    object My : DictionaryRoute(path = "my")
    object Search : DictionaryRoute(path = "search")
}