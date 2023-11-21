package com.example.aftermathandroid.presentation.screens.dictionary.delete

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.provider.rootSnackbar
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.DictionaryInfoDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteDictionaryDialog(
    dictionaryInfo: DictionaryInfoDto,
    onClose: (isDeleted: Boolean) -> Unit = {},
    viewModel: DeleteDictionaryViewModel = hiltViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()
    val snackbarHost = rootSnackbar()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorFlow.collect {
            snackbarHost.showSnackbar(it.message)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.completedFlow.collect {
            onClose(true)
        }
    }

    AlertDialog(onDismissRequest = { onClose(false) }) {
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.md)
            )
        ) {
            Column(
                modifier = Modifier.padding(Dimens.md)
            ) {
                Text(
                    text = stringResource(id = R.string.deleteDictionary),
                    style = MaterialTheme.typography.headlineMedium
                )
                Gap.Md()
                Text(text = "${stringResource(id = R.string.deleteDictionaryWarningStart)} ${dictionaryInfo.name}?")
                Gap.Md()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        enabled = !state.value.loading,
                        modifier = Modifier.weight(1f),
                        onClick = { onClose(false) }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Gap.Sm()
                    ElevatedButton(
                        enabled = !state.value.loading,
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.deleteDictionary(dictionaryInfo.id) }) {
                        Text(text = stringResource(id = R.string.delete))
                    }
                }
            }
        }
    }

}