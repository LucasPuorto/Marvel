package com.lucaspuorto.marvel.repository

import com.lucaspuorto.marvel.db.model.FavoriteCharacter
import com.lucaspuorto.marvel.model.CharactersResponse
import retrofit2.Response

interface MarvelRepository {

    suspend fun getCharacters(): Response<CharactersResponse>
    suspend fun getAllFavorites(): List<FavoriteCharacter>
}