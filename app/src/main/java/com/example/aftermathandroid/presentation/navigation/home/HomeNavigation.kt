package com.example.aftermathandroid.presentation.navigation.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.aftermathandroid.presentation.navigation.common.Navigation
import com.example.aftermathandroid.presentation.navigation.util.switch

@Composable
fun createHomeNavigation() = HomeNavigation(checkNotNull(LocalHomeNavController.current))

class HomeNavigation(navController: NavHostController) : Navigation(navController) {
    fun navigateToFeed() {
        navController.navigate(HomeRoute.Game.path) {
            switch(navController)
        }
    }

    fun navigateToDictionaries() {
        navController.navigate(HomeRoute.Dictionaries.path) {
            switch(navController)
        }
    }

    fun navigateToLobbies() {
        navController.navigate(HomeRoute.Lobbies.path) {
            switch(navController)
        }
    }
}