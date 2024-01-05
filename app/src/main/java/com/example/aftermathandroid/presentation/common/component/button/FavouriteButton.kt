package com.example.aftermathandroid.presentation.common.component.button

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R

@Composable
fun FavouriteButton(isFavourite: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavourite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = stringResource(id = R.string.favorite)
        )
    }
}