package com.krushiler.data.storage.dao

import com.krushiler.data.dao.Dao
import com.krushiler.data.storage.database.DatabaseList
import com.krushiler.data.storage.dbo.ChangeTermDboAction
import com.krushiler.data.storage.dbo.Dictionaries
import com.krushiler.data.storage.dbo.DictionaryDbo
import com.krushiler.data.storage.dbo.TermDbo
import com.krushiler.data.storage.dbo.Terms
import com.krushiler.domain.model.PagingData
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DictionaryDao(database: Database) : Dao(database) {
    suspend fun getUserDictionaries(userId: String, pagingData: PagingData): DatabaseList<DictionaryDbo> = dbQuery {
        val request = Dictionaries.select { Dictionaries.authorId eq userId }
        DatabaseList(
            items = request.copy().limit(pagingData.limit, pagingData.offset.toLong())
                .map { Dictionaries.resultRowToDictionary(it) },
            total = request.copy().count().toInt(),
        )
    }

    suspend fun getDictionaries(pagingData: PagingData): DatabaseList<DictionaryDbo> = dbQuery {
        val request = Dictionaries.selectAll()
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

    suspend fun getTerms(dictionaryId: String): List<TermDbo> = dbQuery {
        Terms.select { Terms.dictionaryId eq dictionaryId }.map { Terms.resultRowToTerm(it) }
    }

    suspend fun createDictionary(id: String, authorId: String, name: String, description: String): Boolean = dbQuery {
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
        Dictionaries.deleteWhere { Dictionaries.id eq id } > 0 && Terms.deleteWhere { Terms.dictionaryId eq id } > 0
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
}