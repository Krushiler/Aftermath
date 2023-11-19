package com.example.aftermathandroid.presentation.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel

@Composable
fun RegisterScreen(
    rootNavigation: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner),
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val loadingAnimation = animateBoolAsFloatState(state.value.isLoading)

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(value = state.value.login,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    onValueChange = { viewModel.loginChanged(it) },
                    label = { Text(text = stringResource(id = R.string.login)) })
                Gap.Lg()
                OutlinedTextField(value = state.value.password,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions { viewModel.register() },
                    onValueChange = { viewModel.passwordChanged(it) },
                    label = { Text(text = stringResource(id = R.string.password)) })
                Gap.Lg()
                Button(
                    onClick = { viewModel.register() },
                    enabled = !state.value.isLoading
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
                Gap.Lg()
                TextButton(
                    onClick = { rootNavigation.navigateToLogin() },
                    enabled = !state.value.isLoading
                ) {
                    Text(text = stringResource(id = R.string.loginSuggestion))
                }
            }
            if (state.value.isLoading) LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .scale(loadingAnimation.value, 1f)
            )
        }
    }
}