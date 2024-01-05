package com.example.aftermathandroid.domain.interactor

import com.example.aftermathandroid.data.repository.DictionaryRepository
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.response.PagedResponse
import domain.model.DictionarySearchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryInteractor @Inject constructor(
    private val dictionaryRepository: DictionaryRepository
) {
    suspend fun getMyDictionaries(limit: Int = 10, offset: Int = 0) =
        dictionaryRepository.getMyDictionaries(limit, offset)

    suspend fun getDictionaries(
        limit: Int = 10,
        offset: Int = 0,
        searchData: DictionarySearchData? = null,
        collectionId: String? = null,
        isFavourite: Boolean = false,
    ): PagedResponse<DictionaryInfoDto> {
        if (isFavourite) {
            return dictionaryRepository.getFavouriteDictionaries(limit, offset)
        }
        return dictionaryRepository.getDictionaries(limit, offset, searchData, collectionId)
    }

    suspend fun createDictionary(name: String, description: String) =
        dictionaryRepository.createDictionary(name, description)

    suspend fun updateDictionary(id: String, name: String, description: String, terms: List<TermDto>) =
        dictionaryRepository.updateDictionary(id, name, description, terms)

    suspend fun getDictionary(id: String): DictionaryDto = dictionaryRepository.getDictionary(id)

    suspend fun deleteDictionary(id: String) = dictionaryRepository.deleteDictionary(id)

    suspend fun getCollectionInfo(collectionId: String) = dictionaryRepository.getCollectionInfo(collectionId)

    suspend fun changeFavourite(dictionaryId: String, isFavourite: Boolean) =
        dictionaryRepository.changeFavourite(dictionaryId, isFavourite)
}