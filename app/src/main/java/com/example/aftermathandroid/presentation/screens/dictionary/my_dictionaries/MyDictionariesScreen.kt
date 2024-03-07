package com.example.aftermathandroid.presentation.screens.dictionary.my_dictionaries

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.component.list_item.DictionaryItem
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigation
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.dictionary.createDictionaryNavigation
import com.example.aftermathandroid.presentation.navigation.root.RootNavigation
import com.example.aftermathandroid.presentation.navigation.root.createRootNavigation
import com.example.aftermathandroid.presentation.screens.dictionary.create.CreateDictionaryDialog
import com.example.aftermathandroid.presentation.screens.dictionary.delete.DeleteDictionaryDialog
import com.example.aftermathandroid.presentation.theme.Dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MyDictionariesScreen(
    dictionaryScreenSource: DictionaryScreenSource,
    viewModel: MyDictionariesViewModel = hiltViewModel(),
    dictionaryNavigation: DictionaryNavigation = createDictionaryNavigation(),
    rootNavigation: RootNavigation = createRootNavigation()
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val dictionaryListState = rememberLazyListState()

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    LaunchedEffect(dictionaryListState) {
        snapshotFlow {
            dictionaryListState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collectLatest { index ->
            if (!state.value.isLoading && state.value.dictionaries.hasNext
                && index != null && index >= state.value.dictionaries.size - 2
            ) {
                viewModel.loadMore()
            }
        }
    }

    Scaffold(
        topBar = {
            if (dictionaryScreenSource == DictionaryScreenSource.Main)
                TopAppBar(
                    title = {
                        Text(text = stringResource(id = R.string.myDictionaries))
                    },
                    navigationIcon = {
                        BackButton(onClick = { dictionaryNavigation.back() })
                    },
                )
        },
        floatingActionButton = {
            if (dictionaryScreenSource == DictionaryScreenSource.Main)
                FloatingActionButton(onClick = {
                    viewModel.createDictionary()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = stringResource(id = R.string.createDictionary)
                    )
                }
        },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyColumn(
                state = dictionaryListState,
                verticalArrangement = Arrangement.spacedBy(Dimens.sm),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(Dimens.md),
            ) {
                items(state.value.dictionaries.items, key = { it.id }) { item ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            viewModel.deleteDictionary(item)
                            false
                        }
                    )
                    SwipeToDismiss(
                        modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        background = {},
                        dismissContent = {
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
                                }
                            )
                        }
                    )
                }
                if (state.value.isLoading) {
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
            if (state.value.showCreateDictionary) {
                CreateDictionaryDialog(
                    onDismiss = { viewModel.completedCreateDictionary(false) },
                    onCreatedDictionary = { viewModel.completedCreateDictionary(true) },
                )
            }
            state.value.showDeleteDictionaryState?.let { deleteState ->
                DeleteDictionaryDialog(
                    deleteState.dictionaryInfo,
                    onClose = { viewModel.closeDeleteDictionary(it) }
                )
            }
        }
    }
}