package data.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangeUserRequest(
    val name: String,
    val avatar: String?
)