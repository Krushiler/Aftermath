package com.krushiler.data.repository

import com.krushiler.data.storage.dao.DictionaryDao
import com.krushiler.data.storage.dao.UserDao
import com.krushiler.data.storage.dbo.ChangeTermDboAction
import com.krushiler.domain.model.PagingData
import com.krushiler.util.generateUUID
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.dto.TermInputDto
import data.dto.UserDto
import data.response.PagedResponse

class DictionaryRepository(private val dictionaryDao: DictionaryDao, private val userDao: UserDao) {
    suspend fun getUserDictionaries(user: UserDto, pagingData: PagingData): PagedResponse<DictionaryInfoDto> {
        val dictionaries = dictionaryDao.getUserDictionaries(user.name, pagingData)
        return PagedResponse(
            items = dictionaries.items.map { DictionaryInfoDto(it.id, user, it.name, it.description) },
            total = dictionaries.total,
            page = pagingData.page,
            pageSize = pagingData.limit
        )
    }

    suspend fun getDictionaries(pagingData: PagingData): PagedResponse<DictionaryInfoDto> {
        val dictionaries = dictionaryDao.getDictionaries(pagingData)
        return PagedResponse(
            items = dictionaries.items.map {
                val userDbo = userDao.getUserByLogin(it.authorId)
                val user = userDbo?.let { dbo ->
                    UserDto(
                        dbo.login, dbo.name, dbo.avatar
                    )
                }
                DictionaryInfoDto(it.id, user, it.name, it.description)
            },
            total = dictionaries.total,
            page = pagingData.page,
            pageSize = pagingData.limit
        )
    }

    suspend fun getDictionary(id: String): DictionaryDto? {
        val dictionary = dictionaryDao.getDictionary(id)
        val terms = dictionaryDao.getTerms(id)
        return dictionary?.let {
            DictionaryDto(id,
                it.name,
                it.description,
                terms.map { term -> TermDto(term.id, term.name, term.description) })
        }
    }

    suspend fun createDictionary(user: UserDto, name: String, description: String): DictionaryDto {
        val id = generateUUID()
        if (dictionaryDao.createDictionary(id, user.name, name, description)) {
            return DictionaryDto(id, name, description, emptyList())
        }
        throw IllegalStateException("Failed to create dictionary")
    }

    suspend fun updateDictionary(
        id: String, user: UserDto, name: String, description: String, terms: List<TermInputDto>
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
}