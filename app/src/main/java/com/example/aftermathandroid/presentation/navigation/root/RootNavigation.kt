package com.example.aftermathandroid.presentation.navigation.root

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.screens.auth.login.LoginScreen
import com.example.aftermathandroid.presentation.screens.auth.register.RegisterScreen
import com.example.aftermathandroid.presentation.screens.home.HomeScreen
import com.example.aftermathandroid.util.rememberViewModelStoreOwner

@Composable
fun RootNavigation() {
    val viewModelStoreOwner = rememberViewModelStoreOwner()
    val viewModel: RootNavigationViewModel = rootViewModel()
    val state = viewModel.state.collectAsState()

    val navController = rememberNavController()

    LaunchedEffect(state) {
        navController.navigate(state.value.route.path)
    }

    BackHandler(state.value.prevState != null) {
        viewModel.back()
    }

    NavHost(navController = navController,
        startDestination = state.value.route.path,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable(RootRoute.Home.path) {
            HomeScreen()
        }
        composable(RootRoute.Login.path) {
            LoginScreen()
        }
        composable(
            RootRoute.Registration.path,
            enterTransition = { slideInHorizontally() },
            exitTransition = { slideOutHorizontally() }) {
            RegisterScreen()
        }
    }
}
