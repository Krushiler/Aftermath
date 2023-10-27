package com.krushiler.domain.model

data class PagingData(
    val limit: Int,
    val offset: Int,
) {
    val page: Int get() = (offset + limit) / limit
}