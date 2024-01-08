package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LobbyDto(
    val id: String,
    var name: String,
    var status: LobbyStatus,
    var host: UserDto,
    var dictionary: DictionaryInfoDto?,
    var questions: List<QuestionDto>,
    var players: List<UserDto> = emptyList(),
    val results: MutableMap<String, GameSummaryDto> = mutableMapOf()
)

@Serializable
enum class LobbyStatus {
    STARTED,
    NOT_STARTED
}