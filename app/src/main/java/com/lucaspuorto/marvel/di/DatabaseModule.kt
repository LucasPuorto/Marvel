package com.lucaspuorto.marvel.di

import android.app.Application
import androidx.room.Room
import com.lucaspuorto.marvel.db.FavoriteCharacterDao
import com.lucaspuorto.marvel.db.FavoriteCharactersDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object DatabaseModule {

    val database = module {

        fun provideDatabase(application: Application): FavoriteCharactersDatabase {
            return Room.databaseBuilder(
                application,
                FavoriteCharactersDatabase::class.java,
                "favorite_character_database"
            ).build()
        }

        fun provideCharacterDao(database: FavoriteCharactersDatabase): FavoriteCharacterDao {
            return database.characterDao()
        }

        single { provideDatabase(androidApplication()) }
        single { provideCharacterDao(get()) }
    }
}