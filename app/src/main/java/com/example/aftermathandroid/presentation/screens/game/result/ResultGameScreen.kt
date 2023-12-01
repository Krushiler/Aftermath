package com.example.aftermathandroid.presentation.screens.game.result

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun ResultGameScreen(
    rootNavigationViewModel: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner),
    viewModel: ResultGameViewModel = hiltViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(Dimens.md),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row {
                    Text(
                        text = "${stringResource(id = R.string.time)}:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Gap.Md()
                    Text(
                        text = state.value.time.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
                Gap.Md()
                Row {
                    Text(
                        text = "${stringResource(id = R.string.score)}:",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Gap.Md()
                    Text(
                        text = state.value.score.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                    )
                }
            }
            Box(modifier = Modifier.padding(Dimens.md)) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    onClick = { rootNavigationViewModel.back() },
                ) {
                    Text(text = stringResource(id = R.string.back))
                }
            }
        }
    }
}