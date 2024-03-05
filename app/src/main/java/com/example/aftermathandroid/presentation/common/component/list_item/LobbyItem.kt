package com.example.aftermathandroid.presentation.common.component.list_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.LobbyDto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LobbyItem(lobby: LobbyDto, onPressed: () -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = { onPressed() }) {
        Column(modifier = Modifier.padding(Dimens.md)) {
            Text(text = lobby.name, style = MaterialTheme.typography.titleMedium)
            Gap.Sm()
            lobby.dictionary?.let { dictionary ->
                Text(text = dictionary.name, style = MaterialTheme.typography.bodyMedium)
            }
            Gap.Sm()
            Text(
                text = "${lobby.players.size} ${
                    pluralStringResource(
                        id = R.plurals.playersCount,
                        count = lobby.players.size
                    )
                }",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}