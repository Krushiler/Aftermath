package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.component.dictionary.DictionaryItem
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDictionariesScreen(
    viewModel: MyDictionariesViewModel = hiltViewModel(),
    dictionaryNavigation: DictionaryNavigationViewModel = rootViewModel(),
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val loadingAnimation = animateBoolAsFloatState(state.value.isLoading)

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "My dictionaries")
                },
                navigationIcon = {
                    BackButton(onClick = { dictionaryNavigation.back() })
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.createDictionary()
            }) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Create dictionary")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dimens.sm),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.md),
            ) {
                items(state.value.dictionaries) { item ->
                    DictionaryItem(dictionary = item)
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