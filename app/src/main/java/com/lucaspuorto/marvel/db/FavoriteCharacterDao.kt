package com.lucaspuorto.marvel.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.lucaspuorto.marvel.db.model.FavoriteCharacter

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM favorite_character")
    fun getAll(): List<FavoriteCharacter>

    @Insert
    fun insert(character: FavoriteCharacter)

    @Delete
     fun delete(character: FavoriteCharacter)
}