package com.example.aftermathandroid.presentation.screens.game.result.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.theme.Dimens
import data.dto.QuestionSummaryDto

@Composable
fun QuestionSummaryItem(data: QuestionSummaryDto) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
            .padding(Dimens.md)
    ) {
        Text(text = data.question.question.text)
        Gap.Md()
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            data.question.answers.forEachIndexed { it, _ ->
                Box(
                    modifier = Modifier.padding(
                        top = if (it == 0) 0.dp else Dimens.sm,
                    )
                ) {
                    AnswerSummaryChip(
                        text = data.question.answers[it].text,
                        isCorrect = if (data.selectedAnswer?.termId == data.question.answers[it].termId) {
                            data.question.answers[it].termId == data.question.question.termId
                        } else if (data.question.answers[it].termId == data.question.question.termId) {
                            true
                        } else null
                    )
                }
            }
        }
    }
}