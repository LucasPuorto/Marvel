package com.lucaspuorto.marvel.presentation

import androidx.lifecycle.Observer
import com.lucaspuorto.marvel.data.repository.MarvelRepository
import com.lucaspuorto.marvel.data.response.MarvelCharacterResponse
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewmodel.HomeViewModel
import io.mockk.coEvery
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat

object HomeViewModelRobot {

    class HomeViewModelRobotArrange {
        fun mockFetchCharacterRepositorySuccess(repository: MarvelRepository) {
            coEvery {
                repository.fetchCharacter(any())
            } returns MarvelCharacterResponse()
        }
    }

    class HomeViewModelRobotAct {
        fun requestCharacter(
            subject: HomeViewModel,
            observe: Observer<StateResponse<CharacterViewData>>
        ) {
            subject.fetchCharacter("spider-man")
            subject.characterLiveData.removeObserver(observe)
        }
    }

    class HomeViewModelRobotAssert {
        fun isFetchCharacterSuccess(subject: HomeViewModel) {
            assertThat(subject.characterLiveData.value, instanceOf(StateSuccess::class.java))
        }
    }

    fun arrange(func: HomeViewModelRobotArrange.() -> Unit) =
        HomeViewModelRobotArrange().apply(func)

    fun act(func: HomeViewModelRobotAct.() -> Unit) =
        HomeViewModelRobotAct().apply(func)

    fun assert(func: HomeViewModelRobotAssert.() -> Unit) =
        HomeViewModelRobotAssert().apply(func)
}