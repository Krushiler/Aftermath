package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class TermDto(
    val id: String,
    val name: String,
    val description: String,
)