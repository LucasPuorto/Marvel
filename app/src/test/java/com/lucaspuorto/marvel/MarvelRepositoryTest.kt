package com.lucaspuorto.marvel

import com.lucaspuorto.marvel.api.MarvelApi
import com.lucaspuorto.marvel.model.CharacterDataContainerResponse
import com.lucaspuorto.marvel.model.CharacterResponse
import com.lucaspuorto.marvel.model.CharactersResponse
import com.lucaspuorto.marvel.model.ThumbnailResponse
import com.lucaspuorto.marvel.repository.MarvelRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class MarvelRepositoryTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelApi::class.java)

    private val repository = MarvelRepositoryImpl(api, "", "", "", mockk())

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `should fetch characters correctly given 200 response`() {
        mockWebServer.enqueueResponse("characters-200.json", 200)
        runBlocking {
            val actual = repository.getCharacters()
            val expected = mockSuccessResponse()
            assertEquals(expected, actual.body())
        }
    }

    private fun mockSuccessResponse(): CharactersResponse =
         CharactersResponse(
            CharacterDataContainerResponse(
                listOf(
                    CharacterResponse(
                        id = 1011793,
                        name = "Belasco",
                        description = "Only the blackest of hearts would dare delve into the dark magic of the Elder Gods, but 13th Century sorcerer Belasco was of just such a heart.",
                        thumbnail = ThumbnailResponse(
                            path = "http://i.annihil.us/u/prod/marvel/i/mg/a/20/4ce5a878b487c",
                            extension = "jpg"
                        )
                    )
                )
            )
        )

    private fun MockWebServer.enqueueResponse(fileName: String, code: Int) {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$fileName")

        val source = inputStream?.let { inputStream.source().buffer() }
        source?.let {
            enqueue(
                MockResponse()
                    .setResponseCode(code)
                    .setBody(source.readString(StandardCharsets.UTF_8))
            )
        }
    }
}