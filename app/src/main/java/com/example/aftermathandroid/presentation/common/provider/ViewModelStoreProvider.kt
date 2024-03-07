package com.example.aftermathandroid.presentation.common.provider

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner

val LocalRootStoreOwner = compositionLocalOf<ViewModelStoreOwner?> { null }

@Composable
inline fun <reified VM : ViewModel> storeViewModel(entry: ProvidableCompositionLocal<ViewModelStoreOwner?>) =
    hiltViewModel<VM>(checkNotNull(entry.current))