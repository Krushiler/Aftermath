package com.example.aftermathandroid.presentation.screens.game.flow

data class GameFlowState(
    val gameScreen: GameScreen = GameScreen.COUNTDOWN
)

enum class GameScreen {
    COUNTDOWN,
    GAME,
    RESULT
}