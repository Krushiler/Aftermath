package com.example.aftermathandroid.presentation.screens.game.flow

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.aftermathandroid.presentation.screens.game.countdown.CountdownGameScreen
import com.example.aftermathandroid.presentation.screens.game.question.QuestionGameScreen
import com.example.aftermathandroid.presentation.screens.game.result.ResultGameScreen

@Composable()
fun GameFlowScreen(
    viewModel: GameFlowViewModel = hiltViewModel()
) {
    val state = viewModel.stateFlow.collectAsState()

    BackHandler(enabled = true) {}

    when (state.value.gameScreen) {
        GameScreen.COUNTDOWN -> CountdownGameScreen()
        GameScreen.GAME -> QuestionGameScreen()
        GameScreen.RESULT -> ResultGameScreen()
    }
}