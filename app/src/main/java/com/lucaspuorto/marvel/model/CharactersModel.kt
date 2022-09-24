package com.lucaspuorto.marvel.model

import java.util.Date

data class CharactersModel(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: CharacterDataContainerModel,
    val etag: String?
)

data class CharacterDataContainerModel(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    val results: List<CharacterModel>
)

data class CharacterModel(
    val id: Int,
    val name: String,
    val description: String,
    val modified: Date?,
    val thumbnail: String
)
