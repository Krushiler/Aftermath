package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryInfoDto(
    val id: String,
    val name: String,
    val description: String,
    val author: UserDto?,
    val canEdit: Boolean,
)