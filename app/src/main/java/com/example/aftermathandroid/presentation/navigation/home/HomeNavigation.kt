package com.example.aftermathandroid.presentation.navigation.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel

val LocalHomeStoreOwner = compositionLocalOf<ViewModelStoreOwner?> {
    null
}

@Composable
inline fun <reified VM : ViewModel> homeFlowViewModel() = hiltViewModel<VM>(
    checkNotNull(LocalHomeStoreOwner.current)
)

@Composable
fun HomeNavigation() {
    val viewModel: HomeNavigationViewModel = rootViewModel()
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
        composable(HomeRoute.Settings.path) {
            Scaffold {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Settings")
                }
            }
        }
        composable(HomeRoute.Feed.path) {
            Scaffold {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Feed")
                }
            }
        }
        composable(HomeRoute.Messages.path) {
            Scaffold {
                Column(
                    modifier = Modifier
                        .padding(it)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Messages")
                }
            }
        }
    }
}
