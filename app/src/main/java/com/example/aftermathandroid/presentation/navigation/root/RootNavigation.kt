package com.example.aftermathandroid.presentation.navigation.root

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
                            popUpTo(0)
                        }
                    } else if (it != null && isAuthDestination) {
                        navController.navigate(RootDestination.Home.path) {
                            popUpTo(0)
                        }
                    }
                }
            }

            NavHost(
                navController = navController,
                startDestination = RootDestination.Home.path,
                enterTransition = { slideInHorizontally { it } },
                exitTransition = { slideOutHorizontally { -it / 2 } },
                popEnterTransition = { slideInHorizontally { -it / 2 } },
                popExitTransition = { slideOutHorizontally { it } },
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
                ) {
                    RegisterScreen()
                }
                composable(
                    "${RootDestination.EditDictionary.path}/{dictionaryId}",
                    arguments = listOf(navArgument("dictionaryId") { type = NavType.StringType })
                ) {
                    EditDictionaryScreen()
                }
                composable(
                    RootDestination.SelectDictionary.path,
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
