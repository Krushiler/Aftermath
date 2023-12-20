package com.krushiler.data.storage.dao

import com.krushiler.data.dao.Dao
import com.krushiler.data.storage.database.DatabaseList
import com.krushiler.data.storage.dbo.ChangeTermDboAction
import com.krushiler.data.storage.dbo.Dictionaries
import com.krushiler.data.storage.dbo.DictionaryCollectionDbo
import com.krushiler.data.storage.dbo.DictionaryCollectionDictionaries
import com.krushiler.data.storage.dbo.DictionaryCollections
import com.krushiler.data.storage.dbo.DictionaryDbo
import com.krushiler.data.storage.dbo.TermDbo
import com.krushiler.data.storage.dbo.Terms
import com.krushiler.domain.model.PagingData
import domain.model.DictionarySearchData
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.trim
import org.jetbrains.exposed.sql.update
import util.generateUUID

class DictionaryDao(database: Database) : Dao(database) {
    suspend fun getUserDictionaries(userId: String, pagingData: PagingData): DatabaseList<DictionaryDbo> = dbQuery {
        val request = Dictionaries.select { Dictionaries.authorId eq userId }
        DatabaseList(
            items = request.copy().limit(pagingData.limit, pagingData.offset.toLong())
                .map { Dictionaries.resultRowToDictionary(it) },
            total = request.copy().count().toInt(),
        )
    }

    suspend fun getDictionaries(
        pagingData: PagingData,
        searchData: DictionarySearchData,
        collectionId: String?
    ): DatabaseList<DictionaryDbo> = dbQuery {
        val request = Dictionaries.selectAll()
        if (searchData.authors.isNotEmpty()) {
            request.andWhere {
                Dictionaries.authorId inList searchData.authors
            }
        }
        if (searchData.excludeAuthors.isNotEmpty()) {
            request.andWhere {
                Dictionaries.authorId notInList searchData.excludeAuthors
            }
        }
        if (searchData.query.isNotBlank()) {
            request.andWhere {
                (Dictionaries.name.lowerCase().trim() like "%${searchData.query.lowercase().trim()}%").or {
                    Dictionaries.description.lowerCase().trim() like "%${searchData.query.lowercase().trim()}%"
                }
            }
        }
        if (collectionId != null) {
            request.andWhere {
                Dictionaries.id inList
                        DictionaryCollectionDictionaries.select { DictionaryCollectionDictionaries.collectionId eq collectionId }
                            .map {
                                it[DictionaryCollectionDictionaries.dictionaryId]
                            }
            }
        }
        DatabaseList(
            items = request.copy().limit(
                pagingData.limit, pagingData.offset.toLong()
            ).map { Dictionaries.resultRowToDictionary(it) },
            total = request.copy().count().toInt(),
        )
    }

    suspend fun getDictionary(id: String): DictionaryDbo? = dbQuery {
        Dictionaries.select { Dictionaries.id eq id }.map { Dictionaries.resultRowToDictionary(it) }.singleOrNull()
    }

    suspend fun getDictionariesNotPaged(collectionId: String): List<DictionaryDbo> = dbQuery {
        Dictionaries.select {
            Dictionaries.id inList DictionaryCollectionDictionaries
                .select { DictionaryCollectionDictionaries.collectionId eq collectionId }
                .map { it[DictionaryCollectionDictionaries.dictionaryId] }
        }.map { Dictionaries.resultRowToDictionary(it) }
    }

    suspend fun getTerms(dictionaryId: String): List<TermDbo> = dbQuery {
        Terms.select { Terms.dictionaryId eq dictionaryId }.map { Terms.resultRowToTerm(it) }
    }

    suspend fun createDictionary(id: String, authorId: String?, name: String, description: String): Boolean =
        dbQuery {
            Dictionaries.insert {
                it[Dictionaries.name] = name
                it[Dictionaries.description] = description
                it[Dictionaries.authorId] = authorId
                it[Dictionaries.id] = id
            }.insertedCount > 0
        }

    suspend fun createTerm(id: String, dictionaryId: String, term: String, description: String): Boolean = dbQuery {
        Terms.insert {
            it[Terms.name] = term
            it[Terms.description] = description
            it[Terms.dictionaryId] = dictionaryId
            it[Terms.id] = id
        }.insertedCount > 0
    }

    suspend fun deleteDictionary(id: String): Boolean = dbQuery {
        DictionaryCollectionDictionaries.deleteWhere { DictionaryCollectionDictionaries.dictionaryId eq id }
        Dictionaries.deleteWhere { Dictionaries.id eq id } > 0 && Terms.deleteWhere { Terms.dictionaryId eq id } > 0
    }

    suspend fun deleteDictionaryCollection(id: String): Boolean = dbQuery {
        DictionaryCollectionDictionaries.deleteWhere { DictionaryCollectionDictionaries.collectionId eq id }
        DictionaryCollections.deleteWhere { DictionaryCollections.id eq id } > 0
    }

    suspend fun deleteTerm(id: String): Boolean = dbQuery {
        Terms.deleteWhere { Terms.id eq id } > 0
    }

    suspend fun updateDictionary(
        id: String, name: String, description: String, updates: List<ChangeTermDboAction>
    ): Boolean = dbQuery {
        Terms.deleteWhere {
            Terms.dictionaryId eq id
        }
        updates.forEach { action ->
            Terms.insert {
                it[Terms.id] = action.id
                it[Terms.name] = action.name
                it[Terms.description] = action.description
                it[Terms.dictionaryId] = id
            }
        }
        Dictionaries.update({ Dictionaries.id eq id }) {
            it[Dictionaries.name] = name
            it[Dictionaries.description] = description
        } > 0
    }

    suspend fun updateTerm(id: String, term: String, description: String): Boolean = dbQuery {
        Terms.update({ Terms.id eq id }) {
            it[Terms.name] = term
            it[Terms.description] = description
        } > 0
    }

    suspend fun createDictionaryCollection(
        id: String,
        name: String,
    ) = dbQuery {
        DictionaryCollections.insert {
            it[DictionaryCollections.id] = id
            it[DictionaryCollections.name] = name
        }
    }

    suspend fun addDictionaryToCollection(
        dictionaryId: String,
        collectionId: String,
    ) = dbQuery {
        DictionaryCollectionDictionaries.insert {
            it[DictionaryCollectionDictionaries.id] = generateUUID()
            it[DictionaryCollectionDictionaries.dictionaryId] = dictionaryId
            it[DictionaryCollectionDictionaries.collectionId] = collectionId
        }
    }

    suspend fun removeDictionaryFromCollection(
        dictionaryId: String,
        collectionId: String,
    ) = dbQuery {
        DictionaryCollections.deleteWhere {
            (DictionaryCollectionDictionaries.dictionaryId eq dictionaryId).and { DictionaryCollectionDictionaries.collectionId eq collectionId }
        }
    }

    suspend fun getDictionaryCollections(
        pagingData: PagingData
    ): DatabaseList<DictionaryCollectionDbo> = dbQuery {
        val request = DictionaryCollections.selectAll()
        DatabaseList(
            items = request.copy().limit(pagingData.limit, pagingData.offset.toLong())
                .map { DictionaryCollections.resultRowToDictionaryCollection(it) },
            total = request.copy().count().toInt(),
        )
    }

    suspend fun getDictionaryCollection(
        id: String
    ): DictionaryCollectionDbo? = dbQuery {
        DictionaryCollections.select { DictionaryCollections.id eq id }
            .firstOrNull()?.let { DictionaryCollections.resultRowToDictionaryCollection(it) }
    }

    suspend fun getDictionaryCollectionDictionaries(
        collectionId: String,
    ): List<DictionaryDbo> = dbQuery {
        DictionaryCollectionDictionaries.select { DictionaryCollectionDictionaries.collectionId eq collectionId }
            .map {
                Dictionaries.select {
                    Dictionaries.id eq it[DictionaryCollectionDictionaries.dictionaryId]
                }.map { Dictionaries.resultRowToDictionary(it) }.firstOrNull()
            }.mapNotNull { it }
    }
}