package com.example.aftermathandroid.presentation.screens.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel

@Composable
fun RegisterScreen(
    rootNavigation: RootNavigationViewModel = rootViewModel(),
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(value = state.value.login,
                onValueChange = { viewModel.loginChanged(it) },
                label = { Text(text = "Login") })
            Gap.Lg()
            OutlinedTextField(value = state.value.password,
                onValueChange = { viewModel.passwordChanged(it) },
                label = { Text(text = "Password") })
            Gap.Lg()
            Button(onClick = {
                viewModel.register()
            }) {
                Text(text = "Register")
            }
            Gap.Lg()
            TextButton(onClick = { rootNavigation.navigateToLogin() }) {
                Text(text = "Already have an account?")
            }
        }
    }
}