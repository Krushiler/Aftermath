package com.example.aftermathandroid.presentation.screens.game.question

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aftermathandroid.domain.interactor.GameInteractor
import com.example.aftermathandroid.domain.model.LocalGameState
import dagger.hilt.android.lifecycle.HiltViewModel
import data.dto.QuestionItemDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionGameViewModel @Inject constructor(
    private val gameInteractor: GameInteractor
) : ViewModel() {
    private val _stateFlow = MutableStateFlow(QuestionGameState())
    val stateFlow: StateFlow<QuestionGameState> = _stateFlow

    init {
        observeGame()
    }

    fun giveAnswer(questionItemDto: QuestionItemDto) {
        gameInteractor.giveAnswer(questionItemDto)
    }

    private fun observeGame() {
        gameInteractor.apply {
            viewModelScope.launch {
                localGameState.collect { gameState ->
                    if (gameState is LocalGameState.Question) {
                        _stateFlow.update {
                            it.copy(
                                questionText = gameState.question.question.text,
                                answers = gameState.question.answers.map { questionItem ->
                                    AnswerUiModel(
                                        questionItem = questionItem,
                                        answerUiState = if (gameState.correctAnswer?.termId == questionItem.termId)
                                            AnswerUiState.Correct else if (gameState.answeredQuestion?.termId == questionItem.termId)
                                            AnswerUiState.Incorrect else AnswerUiState.NotAnswered
                                    )
                                },
                            )
                        }
                    }
                }
            }
            viewModelScope.launch {
                timeSecondsFlow.collect { time ->
                    _stateFlow.update { it.copy(timeSeconds = time) }
                }
            }
            viewModelScope.launch {
                userScoreState.collect { score ->
                    _stateFlow.update { it.copy(score = score) }
                }
            }
        }
    }
}