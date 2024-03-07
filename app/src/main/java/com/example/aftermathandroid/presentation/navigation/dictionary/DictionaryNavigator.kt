package com.example.aftermathandroid.presentation.navigation.dictionary

import androidx.activity.compose.BackHandler
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.aftermathandroid.presentation.screens.dictionary.collection.DictionaryCollectionScreen
import com.example.aftermathandroid.presentation.screens.dictionary.menu.DictionariesMenuScreen
import com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries.MyDictionariesScreen
import com.example.aftermathandroid.presentation.screens.dictionary.search.SearchDictionaryScreen

val LocalDictionaryNavController = compositionLocalOf<NavHostController?> { null }

@Composable
fun DictionaryNavigator(
    dictionaryScreenSource: DictionaryScreenSource = DictionaryScreenSource.Main,
    navController: NavHostController = rememberNavController(),
    initialLocation: DictionaryRoute? = null
) {
    BackHandler(navController.previousBackStackEntry != null) {
        navController.popBackStack()
    }

    CompositionLocalProvider(
        LocalDictionaryNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = initialLocation?.path() ?: DictionaryRoute.Menu.path(),
            enterTransition = { fadeIn() },
            exitTransition = { fadeOut() },
            popEnterTransition = { fadeIn() },
            popExitTransition = { fadeOut() },
        ) {
            composable(DictionaryDestination.Menu.path) {
                DictionariesMenuScreen(
                    dictionaryScreenSource = dictionaryScreenSource
                )
            }
            composable(DictionaryDestination.My.path) {
                MyDictionariesScreen(
                    dictionaryScreenSource = dictionaryScreenSource,
                    viewModel = hiltViewModel(),
                )
            }
            composable(DictionaryDestination.Search.path) {
                SearchDictionaryScreen(
                    dictionaryScreenSource = dictionaryScreenSource
                )
            }
            composable("${DictionaryDestination.Collection.path}/{collectionId}") {
                DictionaryCollectionScreen(
                    dictionaryScreenSource = dictionaryScreenSource
                )
            }
            composable(
                DictionaryDestination.Favourite.path,
                arguments = listOf(
                    navArgument("isFavourite") {
                        type = NavType.BoolType
                        defaultValue = true
                    }
                )
            ) {
                DictionaryCollectionScreen(
                    dictionaryScreenSource = dictionaryScreenSource
                )
            }
        }
    }
}