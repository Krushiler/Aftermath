package domain.model

data class DictionarySearchData(
    val authors: List<String> = emptyList(),
    val excludeAuthors: List<String> = emptyList(),
    val query: String = "",
)
