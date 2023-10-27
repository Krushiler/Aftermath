package data.response

import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    val items: List<T>,
    val total: Int,
    val page: Int,
    val pageSize: Int,
) {
    val hasNext: Boolean get() = page * pageSize < total
}