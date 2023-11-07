package domain.model

data class DictionarySearchData(
    val authors: List<String>,
    val excludeAuthors: List<String>,
    val query: String,
)
