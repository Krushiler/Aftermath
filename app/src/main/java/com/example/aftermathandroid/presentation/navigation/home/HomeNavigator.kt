package com.example.aftermathandroid.presentation.navigation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.navigation.root.RootRoute
import com.example.aftermathandroid.presentation.screens.dictionary.flow.DictionariesFlowScreen
import com.example.aftermathandroid.presentation.screens.game.lobby_list.LobbyListScreen
import com.example.aftermathandroid.presentation.screens.game.prepare.PrepareGameScreen

val LocalHomeNavController = compositionLocalOf<NavHostController?> { null }

@Composable
fun HomeNavigator(
    navController: NavHostController = rememberNavController(),
) {
    BackHandler(navController.previousBackStackEntry != null) {
        if (!navController.popBackStack()) {
            navController.navigateUp()
        }
    }

    CompositionLocalProvider(
        LocalHomeNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = HomeRoute.Game.path,
            route = RootRoute.Home.path(),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() },
        ) {
            composable(HomeRoute.Game.path) {
                PrepareGameScreen()
            }
            composable(HomeRoute.Dictionaries.path) {
                DictionariesFlowScreen()
            }
            composable(HomeRoute.Lobbies.path) {
                LobbyListScreen()
            }
        }
    }
}

