package com.example.aftermathandroid.presentation.common.component.dictionary

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.TermDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermEditingItem(
    term: TermDto,
    onPressed: () -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onPressed() },
    ) {
        Column(modifier = Modifier.padding(Dimens.md)) {
            Text(text = term.name, style = MaterialTheme.typography.titleMedium)
            Gap.Md()
            Text(text = term.description, style = MaterialTheme.typography.bodyMedium)
        }
    }
}