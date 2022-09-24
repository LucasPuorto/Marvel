package com.lucaspuorto.marvel.viewmodel.mapper

import com.lucaspuorto.marvel.model.CharacterDataContainerModel
import com.lucaspuorto.marvel.model.CharacterDataContainerResponse
import com.lucaspuorto.marvel.model.CharacterModel
import com.lucaspuorto.marvel.model.CharacterResponse
import com.lucaspuorto.marvel.model.CharactersModel
import com.lucaspuorto.marvel.model.CharactersResponse

class CharacterResponseMapper {

    fun transform(response: CharactersResponse?): CharactersModel =
        response?.let {
            mapCharacterDataContainer(response.data)?.let { data ->
                CharactersModel(
                    code = response.code,
                    status = response.status,
                    copyright = response.copyright,
                    attributionText = response.attributionText,
                    attributionHTML = response.attributionHTML,
                    data = data,
                    etag = response.etag
                )
            } ?: throw Exception("CharacterDataContainerResponse is null")
        } ?: throw Exception("CharactersResponse is null")

    private fun mapCharacterDataContainer(data: CharacterDataContainerResponse?): CharacterDataContainerModel? =
        data?.let {
            CharacterDataContainerModel(
                offset = data.offset,
                limit = data.limit,
                total = data.total,
                count = data.count,
                results = mapCharacter(data.results)
            )
        }

    private fun mapCharacter(results: List<CharacterResponse>?): List<CharacterModel> =
        results?.map { result ->
            result.id?.let { id ->
                result.name?.let { name ->
                    result.description?.let { description ->
                        result.thumbnail?.let {
                            result.thumbnail.path?.let { thumbnailsPath ->
                                result.thumbnail.extension?.let { thumbnailExtension ->
                                    CharacterModel(
                                        id = id,
                                        name = name,
                                        description = description,
                                        modified = result.modified,
                                        thumbnail = "${thumbnailsPath}.${thumbnailExtension}".replace("http","https")
                                    )
                                } ?: throw Exception("CharacterResponse.thumbnail.extension is null")
                            } ?: throw Exception("CharacterResponse.thumbnail.path is null")
                        } ?: throw Exception("CharacterResponse.thumbnail is null")
                    } ?: throw Exception("CharacterResponse.description is null")
                } ?: throw Exception("CharacterResponse.name is null")
            } ?: throw Exception("CharacterResponse.id is null")
        } ?: throw Exception("List<CharacterResponse> is null")
}