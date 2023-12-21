package com.example.aftermathandroid.presentation.screens.game.result.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ResultGameInfoView(
    time: Int = 0,
    score: Int = 0,
    totalQuestions: Int = 0,
) {
    val timeFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    val timeString = timeFormat.format(Date(time.toLong() * 1000))

    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.time))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = timeString)
        }
        Gap.Sm()
        Divider()
        Gap.Sm()
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.score))
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "$score/$totalQuestions")
        }
        Gap.Sm()
        Divider()
    }
}