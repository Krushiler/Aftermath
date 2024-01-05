package data.request

import kotlinx.serialization.Serializable

@Serializable
data class ChangeFavouriteRequest(
    val isFavourite: Boolean,
    val dictionaryId: String
)
