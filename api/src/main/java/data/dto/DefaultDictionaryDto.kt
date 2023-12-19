package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DefaultDictionaryDto(
    val id: String,
    val name: String,
    val description: String,
    val terms: List<DefaultTermDto>,
)

@Serializable
data class DefaultTermDto(
    val name: String,
    val description: String,
)
