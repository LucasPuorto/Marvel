package com.lucaspuorto.marvel.repository

import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.model.CharactersResponse
import retrofit2.Response

interface MarvelRepository {

    suspend fun getCharacters(): Response<CharactersResponse>
    suspend fun getAllFavorites(): List<CharacterModel>
    suspend fun addAsFavorite(character: CharacterModel)
    suspend fun removeAsFavorite(character: CharacterModel)
}