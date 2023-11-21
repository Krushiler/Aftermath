package com.example.aftermathandroid.presentation.common.component.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun AnswerButton(
    text: String,
    isCorrect: Boolean? = null,
    onClick: () -> Unit
) {
    ElevatedButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick() },
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = when (isCorrect) {
                true -> Color.Green
                false -> Color.Red
                else -> MaterialTheme.colorScheme.primary
            },
            contentColor = if (isCorrect != null) Color.Black else MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Text(text = text)
    }
}