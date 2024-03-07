package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.domain.model.LocalGameState
import data.dto.GameSummaryDto
import data.dto.QuestionItemDto
import data.dto.QuestionSummaryDto
import domain.game.GameTermsSource
import domain.game.TakeTermResult
import domain.model.GameInitParams
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalGameRepository @Inject constructor(
    private val coroutineScope: CoroutineScope
) {
    private val _userScoreState = MutableStateFlow(0)
    val userScoreState: StateFlow<Int> get() = _userScoreState

    private val _localGameState = MutableStateFlow<LocalGameState>(LocalGameState.WaitingForStart(3))
    val localGameState: StateFlow<LocalGameState> get() = _localGameState

    private val _timeSecondsFlow = MutableStateFlow(0)
    val timeSecondsFlow: StateFlow<Int> get() = _timeSecondsFlow

    private var _termsSource: GameTermsSource? = null
    private val termsSource: GameTermsSource get() = _termsSource ?: GameTermsSource(emptyList(), 0)

    private var _gameParams: GameInitParams? = null
    private val gameParams: GameInitParams get() = _gameParams!!

    private var timerJob: Job? = null

    private val answeredQuestionsSummary = mutableListOf<QuestionSummaryDto>()

    fun startGame(gameInitParams: GameInitParams) {
        reset()
        coroutineScope.launch {
            var timeToStart = 3
            _termsSource = GameTermsSource(
                gameInitParams.dictionaryDto?.terms.orEmpty(),
                gameInitParams.questionsCount,
                gameInitParams.questions
            )
            _gameParams = gameInitParams

            while (timeToStart > 0) {
                _localGameState.value = LocalGameState.WaitingForStart(timeToStart)
                delay(1000)
                timeToStart -= 1
            }

            timerJob = coroutineScope.launch {
                while (true) {
                    _timeSecondsFlow.value += 1
                    delay(1000)
                }
            }

            nextTerm()
        }
    }

    fun giveAnswer(answer: QuestionItemDto) {
        val state = _localGameState.value
        if (state is LocalGameState.Question && state.answeredQuestion == null) {
            coroutineScope.launch {
                val question = state.question
                val correctAnswer = question.question
                if (answer.termId == correctAnswer.termId) {
                    _userScoreState.value += 1
                }
                _localGameState.value = LocalGameState.Question(
                    question,
                    answer,
                    correctAnswer
                )
                answeredQuestionsSummary.add(QuestionSummaryDto(question, answer))
                delay(1000)
                nextTerm()
            }
        }
    }

    private fun nextTerm() {
        val termResult = termsSource.takeTerm(gameParams.answersCount)
        if (termResult is TakeTermResult.Success) {
            val question = termResult.question
            _localGameState.value = LocalGameState.Question(question, null, null)
        } else {
            finishGame()
        }
    }

    private fun finishGame() {
        _localGameState.value = LocalGameState.GameOver(
            GameSummaryDto(
                _userScoreState.value,
                _timeSecondsFlow.value,
                answeredQuestionsSummary.toMutableList(),
            )
        )
        timerJob?.cancel()
        timerJob = null
    }

    private fun reset() {
        _localGameState.value = LocalGameState.WaitingForStart(3)
        _userScoreState.value = 0
        _timeSecondsFlow.value = 0
        _termsSource = null
        answeredQuestionsSummary.clear()
    }
}