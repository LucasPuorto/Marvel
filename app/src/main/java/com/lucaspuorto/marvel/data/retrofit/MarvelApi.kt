package com.lucaspuorto.marvel.data.retrofit

import com.lucaspuorto.marvel.data.response.MarvelCharactersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(
        @Query("nameStartsWith") characterName: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 100
    ): MarvelCharactersResponse
}
