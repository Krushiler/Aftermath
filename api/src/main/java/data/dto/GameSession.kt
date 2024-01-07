package data.dto

import kotlinx.serialization.Serializable

@Serializable
class GameSession(
    val userId: String,
    val connectionId: String,
)