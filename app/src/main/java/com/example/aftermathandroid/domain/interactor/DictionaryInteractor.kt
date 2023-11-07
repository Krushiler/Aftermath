package com.example.aftermathandroid.domain.interactor

import com.example.aftermathandroid.data.repository.DictionaryRepository
import data.dto.DictionaryDto
import data.dto.TermDto
import domain.model.DictionarySearchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryInteractor @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend fun getMyDictionaries(limit: Int = 10, offset: Int = 0) =
        dictionaryRepository.getMyDictionaries(limit, offset)

    suspend fun getDictionaries(limit: Int = 10, offset: Int = 0, searchData: DictionarySearchData? = null) =
        dictionaryRepository.getDictionaries(limit, offset, searchData)

    suspend fun createDictionary(name: String, description: String) =
        dictionaryRepository.createDictionary(name, description)

    suspend fun updateDictionary(id: String, name: String, description: String, terms: List<TermDto>) =
        dictionaryRepository.updateDictionary(id, name, description, terms)

    suspend fun getDictionary(id: String): DictionaryDto = dictionaryRepository.getDictionary(id)
}