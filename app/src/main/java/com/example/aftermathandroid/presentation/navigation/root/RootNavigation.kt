package com.example.aftermathandroid.presentation.navigation.root

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.common.NavigationComponent
import com.example.aftermathandroid.presentation.screens.auth.login.LoginScreen
import com.example.aftermathandroid.presentation.screens.auth.register.RegisterScreen
import com.example.aftermathandroid.presentation.screens.dictionary.edit.EditDictionaryScreen
import com.example.aftermathandroid.presentation.screens.dictionary.edit.EditDictionaryViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.select.DictionarySelectScreen
import com.example.aftermathandroid.presentation.screens.game.flow.GameFlowScreen
import com.example.aftermathandroid.presentation.screens.home.flow.HomeFlowScreen
import com.example.aftermathandroid.presentation.screens.home.profile.ProfileScreen
import com.example.aftermathandroid.util.rememberViewModelStoreOwner

@Composable
fun RootNavigation(
    navController: NavHostController = rememberNavController()
) {
    val store = rememberViewModelStoreOwner()
    CompositionLocalProvider(
        LocalRootNavigationOwner provides store,
    ) {
        val viewModel: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
        NavigationComponent(
            viewModel = viewModel,
            navController = navController,
        ) { navController ->

            val authState = viewModel.authState.collectAsState()

            LaunchedEffect(authState) {
                viewModel.authState.collect {
                    val isAuthDestination = navController.currentDestination?.route == RootDestination.Login.path
                            || navController.currentDestination?.route == RootDestination.Registration.path
                    if (it == null && !isAuthDestination) {
                        navController.navigate(RootDestination.Login.path) {
                            popUpTo(RootDestination.Home.path) {
                                inclusive = true
                            }
                        }
                    } else if (it != null && isAuthDestination) {
                        navController.navigate(RootDestination.Home.path) {
                            popUpTo(RootDestination.Home.path) {
                                inclusive = true
                            }
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = RootDestination.Home.path,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popExitTransition = { fadeOut() },
            ) {
                composable(RootDestination.Profile.path) {
                    ProfileScreen()
                }
                composable(RootDestination.Home.path) {
                    HomeFlowScreen()
                }
                composable(RootDestination.Login.path) {
                    LoginScreen()
                }
                composable(
                    RootDestination.Registration.path,
                    enterTransition = { slideInHorizontally { it } },
                    popExitTransition = { slideOutHorizontally { it } },
                    exitTransition = { slideOutHorizontally { it } },
                ) {
                    RegisterScreen()
                }
                composable(
                    "${RootDestination.EditDictionary.path}/{dictionaryId}",
                    arguments = listOf(navArgument("dictionaryId") { type = NavType.StringType })
                ) {
                    val editDictionaryViewModel = hiltViewModel<EditDictionaryViewModel>()
                    editDictionaryViewModel.init(
                        dictionaryId = it.arguments?.getString("dictionaryId") ?: ""
                    )
                    EditDictionaryScreen()
                }
                composable(
                    RootDestination.SelectDictionary.path,
                    enterTransition = { slideInVertically(initialOffsetY = { it }) },
                    exitTransition = { slideOutVertically(targetOffsetY = { it }) },
                    popExitTransition = { slideOutVertically(targetOffsetY = { it }) },
                ) {
                    DictionarySelectScreen()
                }
                composable(
                    RootDestination.Game.path,
                ) {
                    GameFlowScreen()
                }
            }
        }
    }
}
