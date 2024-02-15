package data.dto

import kotlinx.serialization.Serializable

@Serializable
data class DictionaryInfoDto(
    val id: String,
    val name: String,
    val description: String,
    val author: UserDto?,
    val canEdit: Boolean,
    val isFavourite: Boolean,
) {
    companion object {
        fun fromDictionary(dictionary: DictionaryDto): DictionaryInfoDto {
            return DictionaryInfoDto(
                id = dictionary.id,
                name = dictionary.name,
                description = dictionary.description,
                author = dictionary.author,
                canEdit = dictionary.canEdit,
                isFavourite = dictionary.isFavourite
            )
        }
    }
}