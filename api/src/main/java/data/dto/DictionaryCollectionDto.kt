package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryCollectionDto(
    val id: String,
    val name: String,
)
