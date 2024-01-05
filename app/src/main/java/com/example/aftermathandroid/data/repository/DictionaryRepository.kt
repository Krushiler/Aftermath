package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import data.dto.DictionaryCollectionDto
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.request.ChangeFavouriteRequest
import data.request.CreateDictionaryRequest
import data.request.UpdateDictionaryRequest
import data.response.PagedResponse
import domain.model.DictionarySearchData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val api: BaseApi
) {
    suspend fun getMyDictionaries(limit: Int, offset: Int): PagedResponse<DictionaryInfoDto> =
        api.getMyDictionaries(limit, offset)

    suspend fun getDictionaries(
        limit: Int,
        offset: Int,
        searchData: DictionarySearchData? = null,
        collectionId: String? = null,
    ): PagedResponse<DictionaryInfoDto> =
        api.getGeneralDictionaries(limit, offset, searchData, collectionId)

    suspend fun createDictionary(name: String, description: String): DictionaryDto = api.createDictionary(
        CreateDictionaryRequest(name, description)
    )

    suspend fun updateDictionary(
        id: String,
        name: String,
        description: String,
        terms: List<TermDto>
    ): DictionaryDto = api.updateDictionary(
        id,
        UpdateDictionaryRequest(name, description, terms)
    )

    suspend fun getDictionary(id: String): DictionaryDto = api.getDictionary(id)

    suspend fun deleteDictionary(id: String) = api.deleteDictionary(id)

    suspend fun getFavouriteDictionaries(
        limit: Int = 10,
        offset: Int = 0
    ): PagedResponse<DictionaryInfoDto> = api.getFavouriteDictionaries(
        limit,
        offset
    )

    suspend fun changeFavourite(dictionaryId: String, isFavourite: Boolean) = api.changeFavourite(ChangeFavouriteRequest(isFavourite, dictionaryId))

    suspend fun getCollectionInfo(collectionId: String): DictionaryCollectionDto = api.getCollectionInfo(collectionId)
}