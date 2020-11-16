package com.lucaspuorto.marvel.data.repository

import com.lucaspuorto.marvel.data.response.MarvelCharacterResponse
import com.lucaspuorto.marvel.data.retrofit.MarvelApi
import com.lucaspuorto.marvel.data.retrofit.RetrofitService
import com.lucaspuorto.marvel.utils.BASE_URL
import com.lucaspuorto.marvel.utils.Md5
import com.lucaspuorto.marvel.utils.PUBLIC_KEY

class MarvelRepository {

    private val service: MarvelApi = RetrofitService(BASE_URL).create(MarvelApi::class)
    private val ts: String = System.currentTimeMillis().toString()
    private val hash: String = Md5.get(ts)

    suspend fun fetchCharacter(characterName: String): MarvelCharacterResponse =
        service.getCharacter(characterName, ts, hash, PUBLIC_KEY)

    suspend fun fetchComicsList(characterId: String): MarvelCharacterResponse =
        service.getComicsList(characterId, ts, hash, PUBLIC_KEY)
}
