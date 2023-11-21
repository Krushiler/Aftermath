package com.example.aftermathandroid.presentation.screens.game.question

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
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
            Column(
                modifier = Modifier
                    .padding(Dimens.md)
                    .fillMaxSize()
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "${stringResource(id = R.string.time)}: ${state.value.timeSeconds}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "${stringResource(id = R.string.score)}: ${state.value.score}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Gap.Lg()
                Text(text = state.value.questionText, style = MaterialTheme.typography.headlineSmall)
                Gap.Xxl()
                LazyColumn(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(Dimens.xxl)
                ) {
                    items(state.value.answers) { answer ->
                        AnswerButton(
                            text = answer.questionItem.text,
                            isCorrect = when (answer.answerUiState) {
                                AnswerUiState.Correct -> true
                                AnswerUiState.Incorrect -> false
                                else -> null
                            },
                            onClick = {
                                viewModel.giveAnswer(answer.questionItem)
                            }
                        )
                        Gap.Md()
                    }
                }
                Gap.Lg()
            }
        }
    }
}