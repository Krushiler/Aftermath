package data.request

data class ChangeFavouriteRequest(
    val isFavourite: Boolean,
    val dictionaryId: String
)
