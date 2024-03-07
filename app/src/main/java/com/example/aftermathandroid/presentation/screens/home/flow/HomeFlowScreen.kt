package com.example.aftermathandroid.presentation.screens.home.flow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigation
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigator
import com.example.aftermathandroid.presentation.navigation.home.HomeRoute
import com.example.aftermathandroid.presentation.navigation.home.LocalHomeNavController
import com.example.aftermathandroid.presentation.navigation.home.createHomeNavigation

@Composable
fun HomeFlowScreen(
    navController: NavHostController = rememberNavController()
) {
    CompositionLocalProvider(
        LocalHomeNavController provides navController
    ) {
        val navigationState = navController.currentBackStackEntryAsState()
        val homeNavigation: HomeNavigation = createHomeNavigation()
        Scaffold(
            bottomBar = {
                BottomAppBar {
                    NavigationBarItem(
                        selected = navigationState.value?.destination?.route == HomeRoute.Game.path,
                        onClick = { homeNavigation.navigateToFeed() },
                        icon = { Icon(imageVector = Icons.Outlined.PlayArrow, contentDescription = null) },
                        label = { Text(text = stringResource(id = R.string.game)) },
                    )
                    NavigationBarItem(
                        selected = navigationState.value?.destination?.route == HomeRoute.Dictionaries.path,
                        onClick = { homeNavigation.navigateToDictionaries() },
                        icon = { Icon(imageVector = Icons.Outlined.Menu, contentDescription = null) },
                        label = { Text(text = stringResource(id = R.string.dictionaries)) },
                    )
                }
            },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    HomeNavigator(navController = navController)
                }
            }
        )
    }
}