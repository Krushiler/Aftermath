package com.example.aftermathandroid.presentation.common.model

data class PagedList<T>(
    val items: List<T> = emptyList(),
    val hasNext: Boolean = true,
    val offset: Int = 0,
) {
    val size = items.size
}