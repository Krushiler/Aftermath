package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDto(
    val id: String,
    val name: String,
    val description: String,
    val author: UserDto?,
    val canEdit: Boolean,
    val terms: List<TermDto>,
    val isFavourite: Boolean,
)