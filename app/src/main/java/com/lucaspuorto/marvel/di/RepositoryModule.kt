package com.lucaspuorto.marvel.di

import com.lucaspuorto.marvel.BuildConfig
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.repository.MarvelRepositoryImpl
import com.lucaspuorto.marvel.util.Md5
import org.koin.dsl.module

object RepositoryModule {

    val repository = module {

        val timestamp: String = System.currentTimeMillis().toString()
        val hash: String = Md5.get(timestamp)

        factory<MarvelRepository> {
            MarvelRepositoryImpl(get(), timestamp, hash, BuildConfig.PUBLIC_KEY, get())
        }
    }
}