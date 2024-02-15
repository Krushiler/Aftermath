package data.dto

import kotlinx.serialization.Serializable

object GameClientActionType {
    const val START_GAME = "start-game"
    const val SELECT_DICTIONARY = "select-dictionary"
    const val PASS_RESULT = "pass-result"
    const val CONNECT = "connect"
}

@Serializable
sealed class GameClientAction(
    val type: String
) {

    @Serializable
    data class Connect(
        val token: String
    ) : GameClientAction(GameClientActionType.CONNECT)

    @Serializable
    data class SelectDictionary(
        val dictionaryId: String,
        val termCount: Int
    ) : GameClientAction(GameClientActionType.SELECT_DICTIONARY)

    @Serializable
    data class PassResult(
        val summary: GameSummaryDto
    ) : GameClientAction(GameClientActionType.PASS_RESULT)

    @Serializable
    object StartGame : GameClientAction(GameClientActionType.START_GAME)
}