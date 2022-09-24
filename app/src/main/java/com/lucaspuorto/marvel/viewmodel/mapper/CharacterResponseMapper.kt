package com.lucaspuorto.marvel.viewmodel.mapper

import com.lucaspuorto.marvel.model.CharacterModel
import com.lucaspuorto.marvel.model.CharacterResponse

class CharacterResponseMapper {

    fun transform(results: List<CharacterResponse>?): List<CharacterModel> =
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
                                        thumbnail = "${thumbnailsPath}.${thumbnailExtension}".replace("http", "https")
                                    )
                                } ?: throw Exception("CharacterResponse.thumbnail.extension is null")
                            } ?: throw Exception("CharacterResponse.thumbnail.path is null")
                        } ?: throw Exception("CharacterResponse.thumbnail is null")
                    } ?: throw Exception("CharacterResponse.description is null")
                } ?: throw Exception("CharacterResponse.name is null")
            } ?: throw Exception("CharacterResponse.id is null")
        } ?: throw Exception("List<CharacterResponse> is null")
}