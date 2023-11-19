package com.example.aftermathandroid.presentation.navigation.home

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.LocalHomeNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.common.NavigationComponent
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.flow.DictionariesFlowScreen
import com.example.aftermathandroid.presentation.screens.game.prepare.PrepareGameScreen

@Composable
fun HomeNavigation(
    navController: NavHostController = rememberNavController(),
) {
    val viewModel: HomeNavigationViewModel = storeViewModel(LocalHomeNavigationOwner)
    val rootViewModel: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)

    NavigationComponent(
        navController = navController,
        viewModel = viewModel,
        onBackOver = {
            rootViewModel.back()
        }
    ) { controller ->
        NavHost(
            navController = controller,
            startDestination = HomeRoute.Game.path,
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
        }
    }
}

