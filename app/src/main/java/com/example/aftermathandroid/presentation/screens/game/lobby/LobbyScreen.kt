package com.example.aftermathandroid.presentation.screens.game.lobby

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyScreen(
    viewModel: LobbyViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.lobbies)) }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column {
                Text(text = state.value.dictionaryState?.dictionary?.name ?: "")
            }
        }
    }
}