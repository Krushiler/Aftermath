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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.aftermathandroid.presentation.common.component.list_item.DictionaryItem
import com.example.aftermathandroid.presentation.common.component.field.AppBarTextField
import com.example.aftermathandroid.presentation.common.provider.LocalDictionaryNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchDictionaryScreen(
    dictionaryScreenSource: DictionaryScreenSource,
    viewModel: SearchDictionaryViewModel = hiltViewModel(),
    dictionaryNavigation: DictionaryNavigationViewModel = storeViewModel(LocalDictionaryNavigationOwner),
    rootNavigation: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner),
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
            if (dictionaryScreenSource == DictionaryScreenSource.Main)
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
                if (dictionaryScreenSource == DictionaryScreenSource.Select) item {
                    OutlinedTextField(
                        value = state.value.query,
                        onValueChange = { viewModel.queryChanged(it) },
                        placeholder = { Text(text = stringResource(id = R.string.search)) },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions { focusManager.clearFocus() }
                    )
                }
                items(state.value.dictionaries.items) { item ->
                    DictionaryItem(
                        dictionary = item,
                        onPressed = {
                            when (dictionaryScreenSource) {
                                DictionaryScreenSource.Main -> {
                                    rootNavigation.navigateToEditDictionary(item.id)
                                }

                                DictionaryScreenSource.Select -> {
                                    viewModel.selectDictionary(item)
                                    rootNavigation.back()
                                }
                            }
                        },
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