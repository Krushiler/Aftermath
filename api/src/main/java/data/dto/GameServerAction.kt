package data.dto

import kotlinx.serialization.Serializable

object GameServerActionType {
    const val USERS_UPDATED = "users-updated"
    const val GAME_STARTED = "game-started"
    const val DICTIONARY_SELECTED = "dictionary-selected"
    const val RESULTS_CHANGED = "results-changed"
}

@Serializable
sealed class GameServerAction(val type: String) {

    @Serializable
    data class UsersUpdated(
        val users: List<UserDto>
    ) : GameServerAction(GameServerActionType.USERS_UPDATED)

    @Serializable
    object GameStarted : GameServerAction(GameServerActionType.GAME_STARTED)

    @Serializable
    data class DictionarySelected(
        val dictionary: DictionaryInfoDto,
        val termCount: Int,
        val questions: List<QuestionDto>,
    ) : GameServerAction(GameServerActionType.DICTIONARY_SELECTED)

    @Serializable
    data class ResultsChanged(
        val summaries: Map<String, GameSummaryDto>,
    ) : GameServerAction(GameServerActionType.RESULTS_CHANGED)
}