package com.example.aftermathandroid.presentation.navigation.dictionary

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.aftermathandroid.presentation.navigation.common.Navigation

@Composable
fun createDictionaryNavigation() = DictionaryNavigation(checkNotNull(LocalDictionaryNavController.current))

class DictionaryNavigation(navController: NavHostController) : Navigation(navController) {
    fun navigateToMyDictionaries() {
        navController.navigate(DictionaryRoute.My.path())
    }

    fun navigateToSearch() {
        navController.navigate(DictionaryRoute.Search.path())
    }

    fun navigateToCollection(collectionId: String) {
        navController.navigate(DictionaryRoute.Collection(collectionId).path())
    }

    fun navigateToFavourite() {
        navController.navigate(DictionaryRoute.Favourite.path())
    }
}