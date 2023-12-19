package com.krushiler.data.storage.dbo

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class DictionaryCollectionDbo(
    val id: String,
    val name: String,
)

object DictionaryCollections : Table() {
    val id = varchar("id", 128)
    val name = varchar("name", 128)

    override val primaryKey = PrimaryKey(id)

    fun resultRowToDictionaryCollection(row: ResultRow) = DictionaryCollectionDbo(
        id = row[id],
        name = row[name],
    )
}

object DictionaryCollectionDictionaries : Table() {
    val id = varchar("id", 128)
    val dictionaryId = varchar("dictionaryId", 128).references(Dictionaries.id)
    val collectionId = varchar("collectionId", 128).references(DictionaryCollections.id)

    override val primaryKey = PrimaryKey(id)
}