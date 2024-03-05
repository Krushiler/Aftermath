package com.example.aftermathandroid.presentation.screens.game.lobby_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.list_item.LobbyItem
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun LobbyListScreen(
    viewModel: LobbyListViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState()
    val snackbarHost = rootSnackbar()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.md),
                verticalArrangement = Arrangement.spacedBy(Dimens.sm)
            ) {
                items(state.value.lobbies) {
                    LobbyItem(it, onPressed = {})
                }
            }
        }
    }
}