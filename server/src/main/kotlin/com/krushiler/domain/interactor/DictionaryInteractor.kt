package com.krushiler.domain.interactor

import com.krushiler.data.repository.DictionaryRepository
import com.krushiler.data.repository.UserRepository
import domain.model.DictionarySearchData
import com.krushiler.domain.model.PagingData
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.response.PagedResponse

class DictionaryInteractor(
    private val userRepository: UserRepository,
    private val dictionaryRepository: DictionaryRepository
) {
    suspend fun getUserDictionaries(userId: String, pagingData: PagingData): PagedResponse<DictionaryInfoDto> {
        val user = userRepository.getUserByLogin(userId)
        return dictionaryRepository.getUserDictionaries(user, pagingData)
    }

    suspend fun getDictionary(id: String, userId: String?): DictionaryDto? {
        return dictionaryRepository.getDictionary(id, userId)
    }

    suspend fun createDictionary(user: String, name: String, description: String): DictionaryDto {
        val userDto = userRepository.getUserByLogin(user)
        return dictionaryRepository.createDictionary(userDto, name, description)
    }

    suspend fun updateDictionary(
        id: String,
        user: String,
        name: String,
        description: String,
        terms: List<TermDto>,
        userId: String,
    ): DictionaryDto? {
        val userDto = userRepository.getUserByLogin(user)
        dictionaryRepository.updateDictionary(id, userDto, name, description, terms)
        return dictionaryRepository.getDictionary(id, userId)
    }

    suspend fun deleteDictionary(id: String, user: String): Boolean {
        val userDto = userRepository.getUserByLogin(user)
        return dictionaryRepository.deleteDictionary(id, userDto)
    }

    suspend fun getDictionaries(
        pagingData: PagingData,
        searchData: DictionarySearchData,
        userId: String?,
        collectionId: String?
    ): PagedResponse<DictionaryInfoDto> {
        return dictionaryRepository.getDictionaries(pagingData, searchData, userId, collectionId)
    }

    suspend fun createInitialDictionaries() = dictionaryRepository.createInitialDictionaries()
}