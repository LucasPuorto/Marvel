package com.lucaspuorto.marvel.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lucaspuorto.marvel.db.model.CharacterModel

@Dao
interface FavoriteCharacterDao {

    @Query("SELECT * FROM favorite_character")
    fun getAll(): List<CharacterModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: CharacterModel)

    @Delete
    suspend fun delete(character: CharacterModel)
}