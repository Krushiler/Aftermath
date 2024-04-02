package com.example.aftermathandroid.presentation.navigation.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.aftermathandroid.presentation.navigation.common.Navigation
import com.example.aftermathandroid.presentation.navigation.util.root

@Composable
fun createRootNavigation() = RootNavigation(checkNotNull(LocalRootNavController.current))

class RootNavigation(navController: NavHostController) : Navigation(navController) {
    fun navigateToProfile() {
        navController.navigate(RootRoute.Profile.path())
    }

    fun navigateToRegister() {
        navController.navigate(RootRoute.Registration.path())
    }

    fun navigateToLogin() {
        navController.navigate(RootRoute.Login.path()) {
            root()
        }
    }

    fun navigateToEditDictionary(dictionaryId: String) {
        navController.navigate(RootRoute.EditDictionary(dictionaryId).path())
    }

    fun navigateToSelectDictionary() {
        navController.navigate(RootRoute.SelectDictionary.path())
    }

    fun navigateToGame() {
        navController.navigate(RootRoute.Game.path())
    }

    fun navigateToLobby() {
        navController.navigate(RootRoute.Lobby.path())
    }
}