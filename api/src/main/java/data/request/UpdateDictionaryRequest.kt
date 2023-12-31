package data.request

import data.dto.TermDto
import kotlinx.serialization.Serializable

@Serializable
data class UpdateDictionaryRequest(
    val name: String,
    val description: String,
    val terms: List<TermDto>,
)