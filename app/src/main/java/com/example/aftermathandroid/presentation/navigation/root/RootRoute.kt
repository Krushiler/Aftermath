package com.example.aftermathandroid.presentation.navigation.root

sealed class RootRoute(val path: String) {
    object Login : RootRoute("login")
    object Registration : RootRoute("registration")
    object Home : RootRoute("home")
}