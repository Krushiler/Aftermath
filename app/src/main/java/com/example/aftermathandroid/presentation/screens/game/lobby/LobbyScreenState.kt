package com.example.aftermathandroid.presentation.screens.game.lobby

import com.example.aftermathandroid.domain.model.GameDictionaryState
import data.dto.UserDto

data class LobbyScreenState(
    val players: List<UserDto> = emptyList(),
    val dictionaryState: GameDictionaryState? = null,
)
