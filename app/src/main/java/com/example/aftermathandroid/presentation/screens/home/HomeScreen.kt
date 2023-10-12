package com.example.aftermathandroid.presentation.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigation
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeRoute
import com.example.aftermathandroid.presentation.navigation.home.LocalHomeStoreOwner
import com.example.aftermathandroid.util.rememberViewModelStoreOwner

@Composable
fun HomeScreen() {
    val viewModelStoreOwner = rememberViewModelStoreOwner()
    val viewModel: HomeNavigationViewModel = rootViewModel()

    val homeNavigationState = viewModel.state.collectAsState()

    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Feed,
                    onClick = { viewModel.navigateToFeed() },
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
                    label = { Text(text = "Feed") },
                )
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Messages,
                    onClick = { viewModel.navigateToMessages() },
                    icon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    label = { Text(text = "Messages") },
                )
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Settings,
                    onClick = { viewModel.navigateToSettings() },
                    icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
                    label = { Text(text = "Settings") },
                )
            }
        },
        content = { padding ->
            CompositionLocalProvider(
                LocalHomeStoreOwner provides viewModelStoreOwner
            ) {
                Column(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    HomeNavigation()
                }
            }
        }
    )
}