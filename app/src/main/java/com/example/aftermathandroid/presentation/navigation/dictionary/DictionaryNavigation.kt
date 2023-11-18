package com.example.aftermathandroid.presentation.navigation.dictionary

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.menu.DictionariesMenuScreen
import com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries.MyDictionariesScreen
import com.example.aftermathandroid.presentation.screens.dictionary.search.SearchDictionaryScreen

@Composable
fun DictionaryNavigation(dictionaryScreenSource: DictionaryScreenSource = DictionaryScreenSource.Main) {
    val viewModel: DictionaryNavigationViewModel = when (dictionaryScreenSource) {
        DictionaryScreenSource.Main -> rootViewModel()
        DictionaryScreenSource.Select -> hiltViewModel()
    }
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
            DictionariesMenuScreen(
                dictionaryScreenSource = dictionaryScreenSource, dictionaryNavigation = viewModel
            )
        }
        composable(DictionaryRoute.My.path) {
            MyDictionariesScreen(
                dictionaryScreenSource = dictionaryScreenSource,
                viewModel = hiltViewModel(),
                dictionaryNavigation = viewModel
            )
        }
        composable(DictionaryRoute.Search.path) {
            SearchDictionaryScreen(
                dictionaryScreenSource = dictionaryScreenSource, dictionaryNavigation = viewModel
            )
        }
    }
}