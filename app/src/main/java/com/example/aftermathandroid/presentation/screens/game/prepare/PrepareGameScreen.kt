package com.example.aftermathandroid.presentation.screens.game.prepare

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun PrepareGameScreen(
    viewModel: PrepareGameViewModel = hiltViewModel(),
    rootNavigation: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
) {
    val state = viewModel.stateFlow.collectAsState()
    val snackbarHost = rootSnackbar()

    LaunchedEffect(viewModel.errorFlow) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    LaunchedEffect(viewModel.gameStartedFlow) {
        viewModel.gameStartedFlow.collect {
            rootNavigation.navigateToGame()
        }
    }

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(
                modifier = Modifier
                    .padding(Dimens.md)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Gap.Xxl()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(id = R.string.questionsCount)}:"
                    )
                    Gap.Md()
                    OutlinedTextField(
                        modifier = Modifier.width(80.dp),
                        value = state.value.questionCountValue,
                        onValueChange = { viewModel.changeQuestionCount(it) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        singleLine = true,
                        enabled = !state.value.loading
                    )
                }
                Gap.Lg()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${stringResource(id = R.string.dictionary)}:")
                    TextButton(
                        onClick = { rootNavigation.navigateToSelectDictionary() },
                        enabled = !state.value.loading
                    ) {
                        Text(text = state.value.dictionary?.name ?: stringResource(id = R.string.selectDictionary))
                    }
                }
                Gap.Lg()
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.startGame() },
                    enabled = state.value.canStartGame
                ) {
                    Text(text = stringResource(id = R.string.startGame))
                }
            }
        }
    }
}