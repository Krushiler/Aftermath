package com.example.aftermathandroid.presentation.navigation.common

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun <Route : NavigationRoute> NavigationComponent(
    navController: NavHostController = rememberNavController(),
    viewModel: NavigationViewModel<out Route>,
    onBackOver: () -> Unit = {},
    navHost: @Composable (navController: NavHostController) -> Unit
) {

    BackHandler(navController.previousBackStackEntry != null) {
        viewModel.back()
    }

    LaunchedEffect(viewModel.state) {
        viewModel.state.collect {
            navController.navigate(it.path()) {
                when (it.routeType) {
                    RouteType.Normal -> {}
                    RouteType.Root -> {
                        popUpTo(0)
                    }

                    RouteType.Switch -> {
                        popUpTo(navController.currentBackStackEntry?.destination?.route ?: return@navigate) {
                            inclusive = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(viewModel.backFlow) {
        viewModel.backFlow.collect {
            if (navController.previousBackStackEntry != null) {
                navController.navigateUp()
            } else {
                onBackOver()
            }
        }
    }

    navHost(navController)
}