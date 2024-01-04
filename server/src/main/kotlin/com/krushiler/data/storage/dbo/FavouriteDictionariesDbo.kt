package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class FavouriteDictionariesDbo(
    val id: String,
    val dictionaryId: String,
    val userId: String,
)

object FavouriteDictionaries : Table() {
    val id = varchar("id", 128)
    val dictionaryId = varchar("dictionaryId", 128)
    val userId = varchar("userId", 128)

    override val primaryKey = PrimaryKey(id)

    fun resultRowToFavouriteDictionary(row: ResultRow) = FavouriteDictionariesDbo(
        id = row[id],
        dictionaryId = row[dictionaryId],
        userId = row[userId],
    )
}