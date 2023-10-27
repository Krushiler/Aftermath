package com.krushiler.data.storage.database

data class DatabaseList<T>(
    val items: List<T>,
    val total: Int,
)