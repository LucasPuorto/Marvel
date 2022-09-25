package com.lucaspuorto.marvel.repository

import com.lucaspuorto.marvel.api.MarvelApi
import com.lucaspuorto.marvel.db.FavoriteCharacterDao
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.model.CharactersResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class MarvelRepositoryImpl(
    private val api: MarvelApi,
    private val timestamp: String,
    private val hash: String,
    private val publicKey: String,
    private val favoriteCharacterDao: FavoriteCharacterDao
) : MarvelRepository {

    override suspend fun getCharacters(): Response<CharactersResponse> =
        withContext(Dispatchers.Default) {
            api.getCharacters(timestamp, hash, publicKey)
        }

    override suspend fun getAllFavorites(): List<CharacterModel> =
        favoriteCharacterDao.getAll()

    override suspend fun addingAsFavorite(character: CharacterModel) {
        favoriteCharacterDao.insert(character)
    }

    override suspend fun removingFromFavorite(character: CharacterModel) {
        favoriteCharacterDao.delete(character)
    }
}