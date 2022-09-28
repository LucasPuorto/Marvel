package com.lucaspuorto.marvel

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.lucaspuorto.marvel.db.FavoriteCharacterDao
import com.lucaspuorto.marvel.db.FavoriteCharactersDatabase
import com.lucaspuorto.marvel.db.model.CharacterModel
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class FavoriteCharactersDatabaseTest : TestCase() {

    private lateinit var database: FavoriteCharactersDatabase
    private lateinit var dao: FavoriteCharacterDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, FavoriteCharactersDatabase::class.java).build()
        dao = database.characterDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeCharacterAndReadAll() = runBlocking {
        val character = CharacterModel(
            id = 1011793,
            name = "Belasco",
            description = "Only the blackest of hearts would dare delve into the dark magic of the Elder Gods, but 13th Century sorcerer Belasco was of just such a heart.",
            thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/a/20/4ce5a878b487c.jpg"
        )

        dao.insert(character)
        val favorites = dao.getAll()
        assertTrue(favorites.contains(character))
    }

    @Test
    @Throws(Exception::class)
    fun writeCharacterAndDelete() = runBlocking {
        val character = CharacterModel(
            id = 1011793,
            name = "Belasco",
            description = "Only the blackest of hearts would dare delve into the dark magic of the Elder Gods, but 13th Century sorcerer Belasco was of just such a heart.",
            thumbnail = "http://i.annihil.us/u/prod/marvel/i/mg/a/20/4ce5a878b487c.jpg"
        )

        dao.insert(character)
        val favorites = dao.getAll()
        assertTrue(favorites.contains(character))
        dao.delete(character)
        val favoritesEmpty = dao.getAll()
        assertFalse(favoritesEmpty.contains(character))
    }
}