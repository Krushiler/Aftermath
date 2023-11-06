package com.example.aftermathandroid.presentation.screens.dictionary.edit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.TermDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChangeTermDialog(
    term: TermDto,
    onChange: (term: TermDto) -> Unit,
    onDelete: (term: TermDto) -> Unit,
    onDismiss: () -> Unit,
) {
    val name = remember { mutableStateOf(term.name) }
    val description = remember { mutableStateOf(term.description) }

    AlertDialog(onDismissRequest = { onDismiss() }) {
        Box(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(Dimens.md)
            )
        ) {
            Column(
                modifier = Modifier.padding(Dimens.md)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.editTerm))
                    IconButton(onClick = { onDelete(term) }) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = stringResource(
                                id = R.string.delete
                            )
                        )
                    }
                }
                Gap.Md()
                OutlinedTextField(value = name.value, onValueChange = { name.value = it }, label = {
                    Text(text = stringResource(id = R.string.name))
                })
                Gap.Md()
                OutlinedTextField(
                    value = description.value, onValueChange = { description.value = it },
                    maxLines = 3,
                    label = {
                        Text(text = stringResource(id = R.string.description))
                    },
                )
                Gap.Md()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(modifier = Modifier.weight(1f), onClick = { onDismiss() }) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Gap.Sm()
                    ElevatedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onChange(
                                term.copy(
                                    name = name.value,
                                    description = description.value
                                )
                            )
                        }) {
                        Text(text = stringResource(id = R.string.confirm))
                    }
                }
            }
        }
    }
}