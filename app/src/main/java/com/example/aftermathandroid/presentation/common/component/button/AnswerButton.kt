package com.example.aftermathandroid.presentation.common.component.button

import androidx.compose.foundation.background
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
        onClick = { onClick() },
        modifier = Modifier.background(
            if (isCorrect == true) Color.Red else if (isCorrect == false) Color.Green else MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text)
    }
}