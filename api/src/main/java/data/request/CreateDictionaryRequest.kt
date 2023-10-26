package data.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateDictionaryRequest(
    val name: String,
    val description: String,
)