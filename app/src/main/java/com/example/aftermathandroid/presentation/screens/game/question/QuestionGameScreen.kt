package com.example.aftermathandroid.presentation.screens.game.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.R
import com.example.aftermathandroid.presentation.common.component.Gap
import com.example.aftermathandroid.presentation.common.component.button.AnswerButton
import com.example.aftermathandroid.presentation.theme.Dimens

@Composable
fun QuestionGameScreen(
    viewModel: QuestionGameViewModel = hiltViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    Scaffold { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column(modifier = Modifier.padding(Dimens.md)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(
                        text = "${stringResource(id = R.string.time)}: ${state.value.timeSeconds}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${stringResource(id = R.string.score)}: ${state.value.score}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Gap.Md()
                Text(text = state.value.questionText, style = MaterialTheme.typography.headlineMedium)
                Gap.Lg()
                LazyColumn(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(Dimens.md)) {
                    items(state.value.answers) { answer ->
                        AnswerButton(
                            text = answer.questionItem.text,
                            isCorrect = if (answer.answerUiState == AnswerUiState.Correct) true
                            else if (answer.answerUiState == AnswerUiState.Incorrect) false
                            else null,
                            onClick = {
                                viewModel.giveAnswer(answer.questionItem)
                            }
                        )
                    }
                }
            }
        }
    }
}