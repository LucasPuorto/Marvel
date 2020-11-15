package com.lucaspuorto.marvel.data.retrofit

import com.lucaspuorto.marvel.data.response.MarvelCharacterResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    suspend fun getCharacter(
        @Query("name") characterName: String,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 1
    ): MarvelCharacterResponse

    @GET("characters/{characterId}/comics")
    fun getComicsList(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("hash") hash: String,
        @Query("apikey") apiKey: String,
        @Query("limit") limit: Int = 100
    ): MarvelCharacterResponse
}
