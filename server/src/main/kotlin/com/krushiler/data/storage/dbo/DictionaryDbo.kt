package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class DictionaryDbo(
    val id: String,
    val authorId: String?,
    val name: String,
    val description: String,
)

object Dictionaries : Table() {
    val id = varchar("id", 128)
    val authorId = varchar("authorId", 128).nullable()
    val name = varchar("name", 128)
    val description = largeText("description")

    override val primaryKey = PrimaryKey(id)

    fun resultRowToDictionary(row: ResultRow) = DictionaryDbo(
        id = row[id],
        authorId = row[authorId],
        name = row[name],
        description = row[description],
    )
}