package com.example.aftermathandroid.presentation.screens.dictionary.collection

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.component.dictionary.DictionaryItem
import com.example.aftermathandroid.presentation.common.provider.LocalDictionaryNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryNavigationViewModel
import com.example.aftermathandroid.presentation.navigation.dictionary.DictionaryScreenSource
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.theme.Dimens
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DictionaryCollectionScreen(
    dictionaryScreenSource: DictionaryScreenSource,
    viewModel: DictionaryCollectionViewModel = hiltViewModel(),
    dictionaryNavigation: DictionaryNavigationViewModel = storeViewModel(LocalDictionaryNavigationOwner),
    rootNavigation: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val dictionaryListState = rememberLazyListState()

    LaunchedEffect(Unit)
    {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    LaunchedEffect(dictionaryListState)
    {
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
                        Text(text = state.value.collectionName)
                    },
                    navigationIcon = {
                        BackButton(onClick = { dictionaryNavigation.back() })
                    },
                )
        },
    )
    { padding ->
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
                    Box(
                        modifier = Modifier.animateItemPlacement()
                    ) {
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
        }
    }
}