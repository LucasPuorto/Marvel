package com.lucaspuorto.marvel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Rule

@ExperimentalCoroutinesApi
abstract class BaseCoroutineTest {
    @get:Rule
    var dispatcherTestRule = DispatcherTestRule()

    @get:Rule
    val instantTestExecutorRule = InstantTaskExecutorRule()

    val testDispatcherProvider = object : DispatcherProvider {
        override fun main(): CoroutineDispatcher = dispatcherTestRule.testDispatcher
        override fun default(): CoroutineDispatcher = dispatcherTestRule.testDispatcher
        override fun io(): CoroutineDispatcher = dispatcherTestRule.testDispatcher
    }

    @After
    fun unMockk() {
        unmockkAll()
    }
}
