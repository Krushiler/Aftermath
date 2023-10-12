package com.example.aftermathandroid.presentation.common.provider

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf

val LocalRootSnackbarState = compositionLocalOf { SnackbarHostState() }

@Composable
fun rootSnackbar() = LocalRootSnackbarState.current