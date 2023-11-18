package com.example.aftermathandroid.presentation.navigation.root

enum class RootDestination(val path: String) {
    Login("login"),
    Registration("registration"),
    Home("home"),
    Profile("profile"),
    EditDictionary("edit-dictionary"),
    SelectDictionary("select-dictionary"),
    Game("game"),
}

sealed class RootRoute(private val destination: RootDestination) {
    open fun path() = destination.path

    object Login : RootRoute(RootDestination.Login)
    object Registration : RootRoute(RootDestination.Registration)
    object Home : RootRoute(RootDestination.Home)
    object Profile : RootRoute(RootDestination.Profile)
    data class EditDictionary(val dictionaryId: String) : RootRoute(RootDestination.EditDictionary)
    object SelectDictionary : RootRoute(RootDestination.SelectDictionary)
    object Game : RootRoute(RootDestination.Game)
}