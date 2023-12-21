package com.example.aftermathandroid.presentation.screens.game.result.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun AnswerSummaryChip(text: String, isCorrect: Boolean?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .background(
                color = when (isCorrect) {
                    true -> Color.Green
                    false -> Color.Red
                    else -> MaterialTheme.colorScheme.primary
                }
            )
            .padding(Dimens.sm),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = Color.Black)
    }
}