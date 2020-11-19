package com.lucaspuorto.marvel.presentation

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.lucaspuorto.marvel.data.repository.MarvelRepository
import com.lucaspuorto.marvel.data.response.Data
import com.lucaspuorto.marvel.data.response.MarvelCharacterResponse
import com.lucaspuorto.marvel.data.response.Results
import com.lucaspuorto.marvel.data.response.Thumbnail
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewdata.ComicsListViewData
import com.lucaspuorto.marvel.presentation.viewmodel.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat

object HomeViewModelRobot {
    private val repository = mockk<MarvelRepository>()
    private lateinit var characterViewData: StateResponse<CharacterViewData>
    private lateinit var comicsViewData: StateResponse<List<ComicsListViewData>>
    private val lifecycle = LifecycleRegistry(mockk()).apply { handleLifecycleEvent(Lifecycle.Event.ON_RESUME) }

    val subject = HomeViewModel(repository)

    class HomeViewModelRobotArrange {
        fun mockSuccessfulCharacterResponse() {
            coEvery { repository.fetchCharacter(any()) }.returns(
                MarvelCharacterResponse(
                    data = Data(
                        results = listOf(
                            Results(
                                id = "123",
                                name = "Spider-Man",
                                description = "Bitten by a radioactive spider, high school student Peter Parker gained the speed, strength and powers of a spider. Adopting the name Spider-Man, Peter hoped to start a career using his new abilities. Taught that with great power comes great responsibility, Spidey has vowed to use his powers to help people",
                                thumbnail = Thumbnail(
                                    path = "http://i.annihil.us/u/prod/marvel/i/mg/3/50/526548a343e4b",
                                    extension = "jpg"
                                )
                            )
                        )
                    )
                )
            )
        }

        fun mockErrorCharacterResponse() {
            coEvery { repository.fetchCharacter(any()) }.throws(RuntimeException())
        }

        fun mockSuccessfulComicsResponse() {
            coEvery { repository.fetchComicsList(any()) }.returns(
                MarvelCharacterResponse(
                    data = Data(
                        results = listOf(
                            Results(
                                name = "Marvel Age Spider-Man Vol. 2: Everyday Hero (Digest)",
                                thumbnail = Thumbnail(
                                    path = "http://i.annihil.us/u/prod/marvel/i/mg/9/20/4bc665483c3aa",
                                    extension = "jpg"
                                )
                            )
                        )
                    )
                )
            )
        }

        fun mockErrorComicsResponse() {
            coEvery { repository.fetchComicsList(any()) }.throws(RuntimeException())
        }
    }

    class HomeViewModelRobotAct {
        fun requestCharacter() {
            subject.getCharacter("spider-man").observe({ lifecycle }) {
                characterViewData = it
            }
        }

        fun requestComics() {
            subject.getComics("1009610").observe({ lifecycle }) {
                comicsViewData = it
            }
        }
    }

    class HomeViewModelRobotAssert {
        fun isCharacterResponseSuccess() {
            assertThat(characterViewData, instanceOf(StateSuccess::class.java))
        }

        fun isCharacterResponseError() {
            assertThat(characterViewData, instanceOf(StateError::class.java))
        }

        fun isComicsResponseSuccess() {
            assertThat(comicsViewData, instanceOf(StateSuccess::class.java))
        }

        fun isComicsResponseError() {
            assertThat(comicsViewData, instanceOf(StateError::class.java))
        }
    }

    fun arrange(func: HomeViewModelRobotArrange.() -> Unit) =
        HomeViewModelRobotArrange().apply(func)

    fun act(func: HomeViewModelRobotAct.() -> Unit) =
        HomeViewModelRobotAct().apply(func)

    fun assert(func: HomeViewModelRobotAssert.() -> Unit) =
        HomeViewModelRobotAssert().apply(func)
}