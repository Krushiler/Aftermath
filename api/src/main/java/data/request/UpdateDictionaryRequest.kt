package data.request

import data.dto.TermInputDto

data class UpdateDictionaryRequest(
    val dictionaryId: String,
    val name: String,
    val description: String,
    val terms: List<TermInputDto>,
)