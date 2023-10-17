package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val login: String,
    val name: String,
    val avatar: String?,
)