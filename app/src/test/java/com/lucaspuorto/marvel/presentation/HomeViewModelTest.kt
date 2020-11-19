package com.lucaspuorto.marvel.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lucaspuorto.marvel.BaseCoroutineTest
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.act
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.arrange
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.assert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomeViewModelTest : BaseCoroutineTest() {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        dispatcherTestRule.testDispatcher.runBlockingTest { block() }

    @Test
    fun `when request character, should expose state success`() {
        runBlockingTest {
            launch {
                arrange { mockSuccessfulCharacterResponse() }
                act { requestCharacter() }
                assert { isCharacterResponseSuccess() }
            }
        }
    }

    @Test
    fun `when request character, should expose state error`() {
        runBlockingTest {
            launch {
                arrange { mockErrorCharacterResponse() }
                act { requestCharacter() }
                assert { isCharacterResponseError() }
            }
        }
    }

    @Test
    fun `when request comics, should expose state success`() {
        runBlockingTest {
            launch {
                arrange { mockSuccessfulComicsResponse() }
                act { requestComics() }
                assert { isComicsResponseSuccess() }
            }
        }
    }

    @Test
    fun `when request comics, should expose state error`() {
        runBlockingTest {
            launch {
                arrange { mockErrorComicsResponse() }
                act { requestComics() }
                assert { isComicsResponseError() }
            }
        }
    }
}
