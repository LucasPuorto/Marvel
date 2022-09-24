package com.lucaspuorto.marvel.di

import com.lucaspuorto.marvel.api.MarvelApi
import org.koin.dsl.module
import retrofit2.Retrofit

object ApiModule {

    val api = module {
        fun provideMarvelApi(retrofit: Retrofit): MarvelApi {
            return retrofit.create(MarvelApi::class.java)
        }
        single { provideMarvelApi(get()) }
    }
}
