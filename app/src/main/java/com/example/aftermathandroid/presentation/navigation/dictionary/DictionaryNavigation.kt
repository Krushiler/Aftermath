package com.example.aftermathandroid.presentation.navigation.dictionary

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.aftermathandroid.presentation.common.provider.LocalDictionaryNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.common.NavigationComponent
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.collection.DictionaryCollectionScreen
import com.example.aftermathandroid.presentation.screens.dictionary.menu.DictionariesMenuScreen
import com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries.MyDictionariesScreen
import com.example.aftermathandroid.presentation.screens.dictionary.search.SearchDictionaryScreen
import com.example.aftermathandroid.util.rememberViewModelStoreOwner

@Composable
fun DictionaryNavigation(
    dictionaryScreenSource: DictionaryScreenSource = DictionaryScreenSource.Main
) {
    val store = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalDictionaryNavigationOwner provides if (dictionaryScreenSource == DictionaryScreenSource.Main) store
        else LocalDictionaryNavigationOwner.current,
    ) {
        val rootViewModel: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
        val viewModel: DictionaryNavigationViewModel = storeViewModel(LocalDictionaryNavigationOwner)
        NavigationComponent(viewModel = viewModel, onBackOver = {
            rootViewModel.back()
        }) { navController ->
            NavHost(
                navController = navController,
                startDestination = DictionaryDestination.Menu.path,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() },
            ) {
                composable(DictionaryDestination.Menu.path) {
                    DictionariesMenuScreen(
                        dictionaryScreenSource = dictionaryScreenSource, dictionaryNavigation = viewModel
                    )
                }
                composable(DictionaryDestination.My.path) {
                    MyDictionariesScreen(
                        dictionaryScreenSource = dictionaryScreenSource,
                        viewModel = hiltViewModel(),
                        dictionaryNavigation = viewModel
                    )
                }
                composable(DictionaryDestination.Search.path) {
                    SearchDictionaryScreen(
                        dictionaryScreenSource = dictionaryScreenSource, dictionaryNavigation = viewModel
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
}