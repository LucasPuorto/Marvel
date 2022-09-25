package com.lucaspuorto.marvel.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lucaspuorto.marvel.db.model.FavoriteCharacter

@Database(entities = [FavoriteCharacter::class], version = 1, exportSchema = false)
abstract class FavoriteCharactersDatabase : RoomDatabase() {
    abstract fun characterDao(): FavoriteCharacterDao
}