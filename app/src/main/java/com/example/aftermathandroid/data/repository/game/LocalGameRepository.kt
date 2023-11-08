package com.example.aftermathandroid.data.repository.game

import com.example.aftermathandroid.domain.model.GameInitParams
import com.example.aftermathandroid.domain.model.LocalGameState
import com.example.aftermathandroid.domain.repository.GameRepository
import data.dto.QuestionDto
import data.dto.QuestionItemDto
import domain.game.GameTermsSource
import domain.game.TakeTermResult
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
) : GameRepository {
    private var userScore = 0

    private val _localGameState = MutableStateFlow<LocalGameState>(LocalGameState.WaitingForStart(3))
    override val localGameState: StateFlow<LocalGameState> get() = _localGameState

    private val _timeSecondsFlow = MutableStateFlow(0)
    override val timeSecondsFlow: StateFlow<Int> get() = _timeSecondsFlow

    private var _termsSource: GameTermsSource? = null
    private val termsSource: GameTermsSource get() = _termsSource ?: GameTermsSource(emptyList())

    private var _gameParams: GameInitParams? = null
    private val gameParams: GameInitParams get() = _gameParams!!

    private var timerJob: Job? = null

    override fun startGame(gameInitParams: GameInitParams) {
        reset()
        coroutineScope.launch {
            var timeToStart = 3

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
        }
    }

    override fun giveAnswer(answer: QuestionItemDto) {
        val state = _localGameState.value
        if (state is LocalGameState.Question) {
            coroutineScope.launch {
                val question = state.question
                val correctAnswer = question.question
                if (answer.termId == correctAnswer.termId) {
                    userScore++
                }
                _localGameState.value = LocalGameState.Question(
                    question,
                    answer,
                    correctAnswer
                )
                delay(1000)
                nextTerm()
            }
        }
    }

    private fun nextTerm() {
        val termResult = termsSource.takeTerm(gameParams.answersCount)
        if (termResult is TakeTermResult.Success) {
            val term = termResult.term
            val question = QuestionDto(
                term = term,
                question = QuestionItemDto(
                    termId = term.id,
                    text = term.description,
                ),
                answers = termResult.answers.map {
                    QuestionItemDto(
                        termId = it.id,
                        text = it.name
                    )
                }
            )
            _localGameState.value = LocalGameState.Question(question, null, null)
        } else {
            finishGame()
        }
    }

    private fun finishGame() {
        _localGameState.value = LocalGameState.GameOver(_timeSecondsFlow.value, _timeSecondsFlow.value)
        timerJob?.cancel()
        timerJob = null
    }

    private fun reset() {
        _localGameState.value = LocalGameState.WaitingForStart(3)
        _termsSource = null
        userScore = 0
    }
}