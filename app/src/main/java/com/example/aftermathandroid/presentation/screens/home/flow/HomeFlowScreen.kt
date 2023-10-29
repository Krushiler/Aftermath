package com.example.aftermathandroid.presentation.screens.home.flow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigation
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeRoute

@Composable
fun HomeFlowScreen(
    homeViewModel: HomeNavigationViewModel = rootViewModel(),
) {
    val homeNavigationState = homeViewModel.state.collectAsState()
    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Game,
                    onClick = { homeViewModel.navigateToFeed() },
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
                    label = { Text(text = "Feed") },
                )
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Dictionaries,
                    onClick = { homeViewModel.navigateToDictionaries() },
                    icon = {
                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = null)
                    },
                    label = { Text(text = "Dictionaries") },
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                HomeNavigation()
            }
        })
}