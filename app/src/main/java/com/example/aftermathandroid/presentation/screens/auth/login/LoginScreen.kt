package com.example.aftermathandroid.presentation.screens.auth.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.root.RootNavigation
import com.example.aftermathandroid.presentation.navigation.root.createRootNavigation
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun LoginScreen(
    rootNavigation: RootNavigation = createRootNavigation(),
    viewModel: LoginViewModel = hiltViewModel()
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val loadingAnimation = animateBoolAsFloatState(state.value.isLoading)

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    fun onLoginPressed() {
        focusManager.clearFocus()
        viewModel.login()
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(Dimens.md)
            ) {
                item {
                    Gap.Xxl()
                    OutlinedTextField(
                        value = state.value.login,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        onValueChange = { viewModel.loginChanged(it) },
                        label = { Text(text = "Login") }
                    )
                    Gap.Lg()
                    OutlinedTextField(
                        value = state.value.password,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions { onLoginPressed() },
                        onValueChange = { viewModel.passwordChanged(it) },
                        label = { Text(text = "Password") }
                    )
                    Gap.Lg()
                    Button(
                        onClick = { onLoginPressed() },
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
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .scale(loadingAnimation.value, 1f)
            )
        }
    }
}