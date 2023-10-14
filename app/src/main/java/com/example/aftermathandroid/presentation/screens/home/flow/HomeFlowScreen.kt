package com.example.aftermathandroid.presentation.screens.home.flow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.component.button.ProfileButton
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigation
import com.example.aftermathandroid.presentation.navigation.home.HomeNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.home.HomeRoute
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeFlowScreen(
    homeViewModel: HomeNavigationViewModel = rootViewModel(),
    rootNavigationViewModel: RootNavigationViewModel = rootViewModel()
) {
    val homeNavigationState = homeViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Aftermath") },
                actions = {
                    ProfileButton(
                        profilePressed = { rootNavigationViewModel.navigateToProfile() },
                        logoutPressed = { homeViewModel.logout() }
                    )
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Feed,
                    onClick = { homeViewModel.navigateToFeed() },
                    icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
                    label = { Text(text = "Feed") },
                )
                NavigationBarItem(
                    selected = homeNavigationState.value.route == HomeRoute.Messages,
                    onClick = { homeViewModel.navigateToMessages() },
                    icon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    label = { Text(text = "Messages") },
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