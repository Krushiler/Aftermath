package com.example.aftermathandroid.presentation.common.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R

@Composable
fun ProfileButton(
    profilePressed: () -> Unit = {},
    logoutPressed: () -> Unit = {}
) {
    val dropdownExpanded = remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize()) {
        IconButton(onClick = { dropdownExpanded.value = !dropdownExpanded.value }) {
            Icon(Icons.Outlined.Person, contentDescription = stringResource(id = R.string.profile))
        }
        DropdownMenu(expanded = dropdownExpanded.value, onDismissRequest = { dropdownExpanded.value = false }) {
            DropdownMenuItem(text = { Text(text = "Profile") }, onClick = {
                profilePressed.invoke()
                dropdownExpanded.value = false
            })
            DropdownMenuItem(text = { Text(text = "Sign Out") }, onClick = {
                logoutPressed.invoke()
                dropdownExpanded.value = false
            })
        }
    }
}