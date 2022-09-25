package com.lucaspuorto.marvel.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_character")
data class FavoriteCharacter(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val firstName: String,
    @ColumnInfo(name = "description") val lastName: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String
)