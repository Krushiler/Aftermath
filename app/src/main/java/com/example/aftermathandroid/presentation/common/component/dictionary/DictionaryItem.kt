package com.example.aftermathandroid.presentation.common.component.dictionary

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.DictionaryInfoDto

@Composable
fun DictionaryItem(dictionary: DictionaryInfoDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.md)
    ) {
        Text(text = dictionary.name, style = MaterialTheme.typography.titleMedium)
        Gap.Md()
        Text(text = dictionary.description, style = MaterialTheme.typography.bodyMedium)
    }
}