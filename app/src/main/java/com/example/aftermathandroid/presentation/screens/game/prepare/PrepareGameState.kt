package com.example.aftermathandroid.presentation.screens.game.prepare

import data.dto.DictionaryInfoDto

data class PrepareGameState(
    val questionCountValue: String = "0",
    val dictionary: DictionaryInfoDto? = null,
    val loading: Boolean = false,
) {
    val canStartGame = dictionary != null && questionCountValue.toIntOrNull() != null
            && questionCountValue.toInt() > 0 && !loading
}