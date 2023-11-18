package com.example.aftermathandroid.presentation.navigation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.flow.DictionariesFlowScreen
import com.example.aftermathandroid.presentation.screens.game.prepare.PrepareGameScreen

@Composable
fun HomeNavigation() {
    val viewModel: HomeNavigationViewModel = rootViewModel()
    val state = viewModel.state.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(state) {
        navController.navigate(state.value.route.path)
    }

    BackHandler(state.value.canPop) {
        viewModel.back()
    }

    NavHost(navController = navController,
        startDestination = state.value.route.path,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable(HomeRoute.Game.path) {
            PrepareGameScreen()
        }
        composable(HomeRoute.Dictionaries.path) {
            DictionariesFlowScreen()
        }
    }
}
