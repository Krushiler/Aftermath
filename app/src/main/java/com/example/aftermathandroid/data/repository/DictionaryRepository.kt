package com.example.aftermathandroid.data.repository

import com.example.aftermathandroid.data.network.api.BaseApi
import data.dto.DictionaryDto
import data.dto.DictionaryInfoDto
import data.dto.TermDto
import data.request.CreateDictionaryRequest
import data.request.UpdateDictionaryRequest
import data.response.PagedResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DictionaryRepository @Inject constructor(
    private val api: BaseApi
) {
    suspend fun getMyDictionaries(limit: Int, offset: Int): PagedResponse<DictionaryInfoDto> =
        api.getMyDictionaries(limit, offset)

    suspend fun getGeneralDictionaries(limit: Int, offset: Int): PagedResponse<DictionaryInfoDto> =
        api.getGeneralDictionaries(limit, offset)

    suspend fun createDictionary(name: String, description: String): DictionaryDto = api.createDictionary(
        CreateDictionaryRequest(name, description)
    )

    suspend fun updateDictionary(
        id: String,
        name: String,
        description: String,
        terms: List<TermDto>
    ): DictionaryDto = api.updateDictionary(
        UpdateDictionaryRequest(id, name, description, terms)
    )

    suspend fun getDictionary(id: String): DictionaryDto = api.getDictionary(id)
}