package com.example.aftermathandroid.presentation.screens.dictionary.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.component.dictionary.DictionaryItem
import com.example.aftermathandroid.presentation.common.component.field.AppBarTextField
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.rootViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDictionaryScreen(
    viewModel: SearchDictionaryViewModel = hiltViewModel(),
    dictionaryNavigation: DictionaryNavigationViewModel = rootViewModel(),
) {
    val state = viewModel.stateFlow.collectAsState()
    val snackbarHost = rootSnackbar()
    val dictionaryListState = rememberLazyListState()
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    LaunchedEffect(dictionaryListState) {
        snapshotFlow {
            dictionaryListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collectLatest { index ->
            if (!state.value.loading && state.value.dictionaries.hasNext && index != null && index >= state.value.dictionaries.size - 2) {
                viewModel.searchDictionary(refresh = false)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppBarTextField(
                        value = state.value.query,
                        onValueChange = { viewModel.queryChanged(it) },
                        hint = stringResource(id = R.string.search),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions { focusManager.clearFocus() }
                    )
                },
                navigationIcon = {
                    BackButton(onClick = { dictionaryNavigation.back() })
                },
            )
        },
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            LazyColumn(
                contentPadding = PaddingValues(Dimens.md),
                verticalArrangement = Arrangement.spacedBy(Dimens.sm),
                state = dictionaryListState
            ) {
                items(state.value.dictionaries.items) { item ->
                    DictionaryItem(
                        dictionary = item,
                        onPressed = {},
                    )
                }
                if (state.value.loading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Dimens.md),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}