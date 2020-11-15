package com.lucaspuorto.marvel.repository

import com.lucaspuorto.marvel.retrofit.MarvelApi
import com.lucaspuorto.marvel.retrofit.RetrofitService
import com.lucaspuorto.marvel.utils.BASE_URL
import com.lucaspuorto.marvel.utils.Md5

class CharactersRepository {

    private val service = RetrofitService(BASE_URL).create(MarvelApi::class)
    private val ts = System.currentTimeMillis().toString()
    private val hash = Md5.get(ts)
}
