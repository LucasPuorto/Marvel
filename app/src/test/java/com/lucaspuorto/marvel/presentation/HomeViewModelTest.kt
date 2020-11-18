package com.lucaspuorto.marvel.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.lucaspuorto.marvel.LifeCycleTestOwner
import com.lucaspuorto.marvel.data.repository.MarvelRepository
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.act
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.arrange
import com.lucaspuorto.marvel.presentation.HomeViewModelRobot.assert
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewmodel.HomeViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val observeCharacterResponse: Observer<StateResponse<CharacterViewData>> = mockk()
    private val repository: MarvelRepository = mockk()

    private lateinit var lifeCycleTestOwner: LifeCycleTestOwner
    private lateinit var subject: HomeViewModel

    @Before
    fun setUp() {
        lifeCycleTestOwner = LifeCycleTestOwner()
        lifeCycleTestOwner.onCreate()
        subject = HomeViewModel(repository)
        subject.characterLiveData.observe(lifeCycleTestOwner, observeCharacterResponse)
    }

    @After
    fun tearDown() {
        lifeCycleTestOwner.onDestroy()
    }

    @Test
    fun testCharacter() {
        arrange {
            mockFetchCharacterRepositorySuccess(repository)
        }
        act {
            requestCharacter(subject, observeCharacterResponse)
        }
        assert {
            isFetchCharacterSuccess(subject)
        }
    }
}