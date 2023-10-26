package com.example.aftermathandroid.presentation.screens.home.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(), rootNavigationViewModel: RootNavigationViewModel = rootViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()
    val columnScrollState = rememberScrollState()
    val snackbarHost = rootSnackbar()
    val loadingAnimation = animateBoolAsFloatState(state.value.loading)
    val confirmButtonAnimation = animateBoolAsFloatState(state.value.canEditProfile)

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Profile") },
            navigationIcon = { BackButton(onClick = { rootNavigationViewModel.back() }) },
            actions = {
                IconButton(
                    onClick = { viewModel.saveChanges() },
                    modifier = Modifier.scale(confirmButtonAnimation.value)
                ) {
                    Icon(imageVector = Icons.Outlined.Check, contentDescription = "Save changes")
                }
                IconButton(onClick = { viewModel.logout() }) {
                    Icon(imageVector = Icons.Outlined.ExitToApp, contentDescription = "Sign Out")
                }
            })
    }) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.md)
                    .verticalScroll(columnScrollState)
            ) {
                Gap.Md()
                OutlinedTextField(
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    value = state.value.name,
                    onValueChange = { viewModel.nameChanged(it) },
                    label = { Text(text = "Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            LinearProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .scale(loadingAnimation.value)
            )
        }
    }
}