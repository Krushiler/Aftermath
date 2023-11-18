package com.example.aftermathandroid.presentation.common.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R

@Composable
fun BackButton(onClick: () -> Unit = {}) = IconButton(onClick = onClick) {
    Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = stringResource(id = R.string.back))
}
