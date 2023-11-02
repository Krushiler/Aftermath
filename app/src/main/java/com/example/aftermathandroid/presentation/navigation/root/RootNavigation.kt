package com.example.aftermathandroid.presentation.navigation.root

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.screens.auth.login.LoginScreen
import com.example.aftermathandroid.presentation.screens.auth.register.RegisterScreen
import com.example.aftermathandroid.presentation.screens.dictionary.edit.EditDictionaryScreen
import com.example.aftermathandroid.presentation.screens.dictionary.edit.EditDictionaryViewModel
import com.example.aftermathandroid.presentation.screens.home.flow.HomeFlowScreen
import com.example.aftermathandroid.presentation.screens.home.profile.ProfileScreen

@Composable
fun RootNavigation() {
    val viewModel: RootNavigationViewModel = rootViewModel()
    val state = viewModel.state.collectAsState()

    val navController = rememberNavController()

    BackHandler(state.value.canPop) {
        viewModel.back()
    }

    NavHost(navController = navController,
        startDestination = state.value.route.path(),
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable(RootDestination.Profile.path,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }) {
            ProfileScreen(viewModel = hiltViewModel())
        }
        composable(RootDestination.Home.path) {
            HomeFlowScreen()
        }
        composable(RootDestination.Login.path) {
            LoginScreen(viewModel = hiltViewModel())
        }
        composable(RootDestination.Registration.path,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }) {
            RegisterScreen(viewModel = hiltViewModel())
        }
        composable(
            RootDestination.EditDictionary.path,
        ) {
            val editDictionaryViewModel = hiltViewModel<EditDictionaryViewModel>()
            LaunchedEffect(Unit) {
                val route = state.value.route as? RootRoute.EditDictionary
                editDictionaryViewModel.init(route?.dictionaryId ?: "")
            }
            EditDictionaryScreen(viewModel = editDictionaryViewModel)
        }
    }
}
