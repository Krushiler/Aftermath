package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthDto(
    val token: String
)
