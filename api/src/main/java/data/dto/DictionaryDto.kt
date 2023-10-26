package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryDto(
    val id: String,
    val name: String,
    val description: String,
    val terms: List<TermDto>,
)