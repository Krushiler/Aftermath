package data.dto

import kotlinx.serialization.Serializable

object GameClientActionType {
    const val JOIN_LOBBY = "join-lobby"
    const val LEAVE_LOBBY = "leave-lobby"
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
        val userId: String
    ) : GameClientAction(GameClientActionType.CONNECT)

    @Serializable
    data class SelectDictionary(
        val dictionaryId: String,
        val termCount: Int
    ) : GameClientAction(GameClientActionType.SELECT_DICTIONARY)

    @Serializable
    data class PassResult(
        val summary: List<QuestionSummaryDto>
    ) : GameClientAction(GameClientActionType.PASS_RESULT)

    @Serializable
    data class JoinLobby(
        val lobbyId: String
    ) : GameClientAction(GameClientActionType.JOIN_LOBBY)

    @Serializable
    data class LeaveLobby(
        val lobbyId: String
    ) : GameClientAction(GameClientActionType.LEAVE_LOBBY)

    @Serializable
    data class StartGame(
        val lobbyId: String
    ) : GameClientAction(GameClientActionType.START_GAME)
}