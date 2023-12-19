package com.example.aftermathandroid.presentation.navigation.dictionary

import com.example.aftermathandroid.presentation.navigation.common.NavigationRoute

enum class DictionaryDestination(val path: String) {
    Menu("menu"),
    My("my"),
    Search("search"),
    Collection("collection")
}

sealed class DictionaryRoute(private val destination: DictionaryDestination) : NavigationRoute() {
    override fun path() = destination.path

    object Menu : DictionaryRoute(destination = DictionaryDestination.Menu)
    object My : DictionaryRoute(destination = DictionaryDestination.My)
    object Search : DictionaryRoute(destination = DictionaryDestination.Search)
    data class Collection(val collectionId: String) : DictionaryRoute(destination = DictionaryDestination.Collection) {
        override fun path() = "${DictionaryDestination.Collection.path}/$collectionId"
    }
}