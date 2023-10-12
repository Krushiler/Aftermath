package com.example.aftermathandroid.presentation.screens.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel

@Composable
fun LoginScreen(
    rootNavigation: RootNavigationViewModel = rootViewModel(), viewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()

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
                OutlinedTextField(
                    value = state.value.login,
                    onValueChange = { viewModel.loginChanged(it) },
                    label = { Text(text = "Login") }
                )
                Gap.Lg()
                OutlinedTextField(
                    value = state.value.password,
                    onValueChange = { viewModel.passwordChanged(it) },
                    label = { Text(text = "Password") }
                )
                Gap.Lg()
                Button(
                    onClick = { viewModel.login() },
                    enabled = !state.value.isLoading
                ) {
                    Text(text = "Login")
                }
                Gap.Lg()
                TextButton(
                    onClick = { rootNavigation.navigateToRegister() },
                    enabled = !state.value.isLoading
                ) {
                    Text(text = "Don't have an account?")
                }
            }
            if (state.value.isLoading) LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            )
        }
    }
}