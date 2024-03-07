package com.example.aftermathandroid.presentation.screens.game.lobby_list

import data.dto.LobbyDto

data class LobbyListScreenState(
    val lobbies: List<LobbyDto> = emptyList(),
    val isLoading: Boolean = false,
)
