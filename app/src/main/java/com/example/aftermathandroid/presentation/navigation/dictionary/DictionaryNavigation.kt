package com.example.aftermathandroid.presentation.navigation.dictionary

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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.menu.DictionariesMenuScreen
import com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries.MyDictionariesScreen

@Composable
fun DictionaryNavigation() {
    val viewModel: DictionaryNavigationViewModel = rootViewModel()
    val state = viewModel.state.collectAsState()
    val navController = rememberNavController()

    BackHandler(state.value.canPop) {
        viewModel.back()
    }

    NavHost(navController = navController,
        startDestination = state.value.route.path,
        enterTransition = { fadeIn() },
        exitTransition = { fadeOut() }) {
        composable(DictionaryRoute.Menu.path) {
            DictionariesMenuScreen()
        }
        composable(DictionaryRoute.My.path) {
            MyDictionariesScreen(viewModel = hiltViewModel())
        }
        composable(DictionaryRoute.Search.path) {
            Scaffold { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Search")
                }
            }
        }
    }
}