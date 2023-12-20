package com.krushiler.data.repository

import com.krushiler.data.storage.dao.DictionaryDao
import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.dbo.ChangeTermDboAction
import com.krushiler.domain.model.PagingData
import data.dto.DefaultDictionaryDto
import data.dto.DictionaryCollectionDto
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.dto.UserDto
import data.response.PagedResponse
import domain.model.DictionarySearchData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import util.generateUUID
import java.io.BufferedReader
import java.io.InputStreamReader

class DictionaryRepository(private val dictionaryDao: DictionaryDao, private val userDao: UserDao) {
    suspend fun getUserDictionaries(user: UserDto, pagingData: PagingData): PagedResponse<DictionaryInfoDto> {
        val dictionaries = dictionaryDao.getUserDictionaries(user.name, pagingData)
        return PagedResponse(
            items = dictionaries.items.map { DictionaryInfoDto(it.id, it.name, it.description, null, true) },
            total = dictionaries.total,
            page = pagingData.page,
            pageSize = pagingData.limit
        )
    }

    suspend fun getDictionaries(
        pagingData: PagingData, searchData: DictionarySearchData, userId: String?, collectionId: String?
    ): PagedResponse<DictionaryInfoDto> {
        val dictionaries = dictionaryDao.getDictionaries(pagingData, searchData, collectionId)
        return PagedResponse(
            items = dictionaries.items.map {
                val userDbo = it.authorId?.let { authorId -> userDao.getUserByLogin(authorId) }
                val user = userDbo?.let { dbo ->
                    UserDto(
                        dbo.login, dbo.name, dbo.avatar
                    )
                }
                DictionaryInfoDto(it.id, it.name, it.description, user, user?.login equalId userId)
            }, total = dictionaries.total, page = pagingData.page, pageSize = pagingData.limit
        )
    }

    suspend fun getDictionary(id: String, userId: String?): DictionaryDto? {
        val dictionary = dictionaryDao.getDictionary(id)
        val terms = dictionaryDao.getTerms(id)
        return dictionary?.let {
            val userDbo = it.authorId?.let { authorId -> userDao.getUserByLogin(authorId) }
            val user = userDbo?.let { dbo ->
                UserDto(
                    dbo.login, dbo.name, dbo.avatar
                )
            }
            DictionaryDto(
                id,
                it.name,
                it.description,
                user,
                user?.login equalId userId,
                terms.map { term -> TermDto(term.id, term.name, term.description) },
            )
        }
    }

    suspend fun createDictionary(user: UserDto, name: String, description: String): DictionaryDto {
        val id = generateUUID()
        if (dictionaryDao.createDictionary(id, user.name, name, description)) {
            return DictionaryDto(id, name, description, user, true, emptyList())
        }
        throw IllegalStateException("Failed to create dictionary")
    }

    suspend fun updateDictionary(
        id: String, user: UserDto, name: String, description: String, terms: List<TermDto>
    ) {
        val dictionary = dictionaryDao.getDictionary(id)
        if (dictionary?.authorId != user.name) throw IllegalArgumentException("Dictionary is not yours")
        if (!dictionaryDao.updateDictionary(id, name, description, terms.map {
                ChangeTermDboAction(id = generateUUID(), name = it.name, description = it.description)
            })) {
            throw IllegalStateException("Failed to update dictionary")
        }
    }

    suspend fun deleteDictionary(id: String, user: UserDto): Boolean {
        val dictionary = dictionaryDao.getDictionary(id)
        if (dictionary?.authorId != user.login) throw IllegalArgumentException("Dictionary is not yours")
        return dictionaryDao.deleteDictionary(id)
    }

    private infix fun String?.equalId(userId: String?): Boolean {
        if (this == null || userId == null) return false
        return this == userId
    }

    suspend fun getCollectionInfo(collectionId: String): DictionaryCollectionDto? {
        val collection = dictionaryDao.getDictionaryCollection(collectionId) ?: return null
        return DictionaryCollectionDto(
            id = collection.id,
            name = collection.name,
        )
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }

    suspend fun createInitialDictionaries() {
        val collectionId = "default"

        dictionaryDao.deleteDictionaryCollection(collectionId)

        dictionaryDao.createDictionaryCollection(
            id = collectionId, name = "Default"
        )

        dictionaryDao.getDictionariesNotPaged(collectionId = collectionId).forEach {
            dictionaryDao.deleteDictionary(it.id)
        }

        val files = listOf(
            "dictionaries/kotlin.json",
            "dictionaries/iot.json",
            "dictionaries/data_analysis.json",
            "dictionaries/math.json",
            "dictionaries/python.json",
        )

        for (file in files) {
            val id = createDictionaryFromResource(file)
            if (id != null) {
                dictionaryDao.addDictionaryToCollection(id, collectionId)
            }
        }
    }

    private suspend fun createDictionaryFromResource(resourceName: String): String? {
        val inputStream = object {}.javaClass.classLoader.getResourceAsStream(resourceName)

        if (inputStream != null) {
            val reader = BufferedReader(InputStreamReader(inputStream))
            val content = reader.readText()
            withContext(Dispatchers.IO) {
                reader.close()
                inputStream.close()
            }

            val json = jsonFormat.decodeFromString<DefaultDictionaryDto>(content)

            val id = generateUUID()

            dictionaryDao.createDictionary(
                id = id,
                authorId = null,
                name = json.name,
                description = json.description,
            )

            json.terms.forEach {
                dictionaryDao.createTerm(
                    id = generateUUID(), dictionaryId = id, term = it.name, description = it.description
                )
            }

            return id
        }
        return null
    }
}