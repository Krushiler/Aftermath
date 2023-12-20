package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class TermDbo(
    val id: String,
    val name: String,
    val description: String,
    val dictionaryId: String,
)

object Terms : Table() {
    val id = varchar("id", 128)
    val name = varchar("term", 128)
    val description = largeText("description")
    val dictionaryId = varchar("dictionaryId", 128)

    override val primaryKey = PrimaryKey(id)

    fun resultRowToTerm(row: ResultRow) = TermDbo(
        id = row[id],
        name = row[name],
        description = row[description],
        dictionaryId = row[dictionaryId],
    )
}

data class ChangeTermDboAction(
    val id: String,
    val name: String,
    val description: String,
)