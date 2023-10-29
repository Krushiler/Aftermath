package com.example.aftermathandroid.presentation.screens.dictionary.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun DictionaryMenuItem(name: String, icon: ImageVector, onPressed: () -> Unit) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(Dimens.sm)
            )
            .clickable {
                onPressed()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.md)
        ) {
            Icon(imageVector = icon, contentDescription = "Icon")
            Gap.Sm()
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}