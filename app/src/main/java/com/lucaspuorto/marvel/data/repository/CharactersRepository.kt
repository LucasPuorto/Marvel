package com.lucaspuorto.marvel.data.repository

import com.lucaspuorto.marvel.data.response.MarvelCharactersResponse
import com.lucaspuorto.marvel.data.retrofit.MarvelApi
import com.lucaspuorto.marvel.data.retrofit.RetrofitService
import com.lucaspuorto.marvel.utils.BASE_URL
import com.lucaspuorto.marvel.utils.Md5
import com.lucaspuorto.marvel.utils.PUBLIC_KEY

class CharactersRepository {

    private val service: MarvelApi = RetrofitService(BASE_URL).create(MarvelApi::class)
    private val ts: String = System.currentTimeMillis().toString()
    private val hash: String = Md5.get(ts)

    suspend fun fetchCharactersList(characterName: String): MarvelCharactersResponse =
        service.getCharacters(characterName, ts, hash, PUBLIC_KEY)
}
