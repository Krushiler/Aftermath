package com.example.aftermathandroid.presentation.screens.dictionary.edit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.animation.animateBoolAsFloatState
import com.example.aftermathandroid.presentation.common.component.button.BackButton
import com.example.aftermathandroid.presentation.common.component.button.FavouriteButton
import com.example.aftermathandroid.presentation.common.component.dictionary.TermEditingItem
import com.example.aftermathandroid.presentation.common.provider.LocalRootNavigationOwner
import com.example.aftermathandroid.presentation.common.provider.storeViewModel
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.navigation.root.RootNavigationViewModel
import com.example.aftermathandroid.presentation.screens.dictionary.edit.component.ChangeTermDialog
import com.example.aftermathandroid.presentation.screens.dictionary.edit.component.CreateTermDialog
import com.example.aftermathandroid.presentation.theme.Dimens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDictionaryScreen(
    viewModel: EditDictionaryViewModel = hiltViewModel(),
    rootNavigation: RootNavigationViewModel = storeViewModel(LocalRootNavigationOwner)
) {
    val snackbarHost = rootSnackbar()
    val state = viewModel.stateFlow.collectAsState()
    val loadingAnimation = animateBoolAsFloatState(targetValue = state.value.loading)

    LaunchedEffect(Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    BackButton(onClick = { rootNavigation.back() })
                },
                title = {
                    BasicTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.value.name,
                        onValueChange = { viewModel.nameChanged(it) },
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onBackground),
                        maxLines = 1,
                        readOnly = !state.value.canEdit
                    )
                },
                actions = {
                    if (state.value.canEdit)
                        IconButton(onClick = { viewModel.save() }) {
                            Icon(
                                imageVector = Icons.Outlined.Done, contentDescription = stringResource(
                                    id = R.string.save
                                )
                            )
                        }
                    FavouriteButton(
                        isFavourite = state.value.isFavourite,
                        onClick = { viewModel.toggleFavourite() }
                    )
                },
            )
        },
        floatingActionButton = {
            if (state.value.canEdit)
                FloatingActionButton(onClick = { viewModel.addTermPressed() }) {
                    Icon(
                        imageVector = Icons.Outlined.Add, contentDescription = stringResource(
                            id = R.string.addTerm
                        )
                    )
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
                contentPadding = PaddingValues(Dimens.md)
            ) {
                item {
                    OutlinedTextField(
                        value = state.value.description,
                        onValueChange = { viewModel.dictionaryDescriptionChanged(it) },
                        maxLines = 3,
                        readOnly = !state.value.canEdit,
                    )
                }
                item { Gap.Sm() }
                items(state.value.terms) { term ->
                    TermEditingItem(
                        term = term,
                        onPressed = { if (state.value.canEdit) viewModel.editTermPressed(term) })
                }
            }
            if (state.value.editTermDialogState != null) {
                ChangeTermDialog(
                    term = state.value.editTermDialogState!!.term,
                    onDismiss = { viewModel.closeEditTermDialog() },
                    onChange = { term ->
                        viewModel.termChanged(term)
                        viewModel.closeEditTermDialog()
                    },
                    onDelete = { term ->
                        viewModel.termDeleted(term)
                        viewModel.closeEditTermDialog()
                    }
                )
            }
            if (state.value.showAddTermDialog) {
                CreateTermDialog(
                    onDismiss = { viewModel.closeAddTermDialog() },
                    onCreate = { name, description ->
                        viewModel.addTerm(name, description)
                        viewModel.closeAddTermDialog()
                    }
                )
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
