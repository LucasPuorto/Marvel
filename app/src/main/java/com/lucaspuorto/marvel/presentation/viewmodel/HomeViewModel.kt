package com.lucaspuorto.marvel.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lucaspuorto.marvel.data.repository.MarvelRepository
import com.lucaspuorto.marvel.presentation.StateError
import com.lucaspuorto.marvel.presentation.StateLoading
import com.lucaspuorto.marvel.presentation.StateResponse
import com.lucaspuorto.marvel.presentation.StateSuccess
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewdata.ComicsListViewData
import com.lucaspuorto.marvel.presentation.viewmapper.CharacterViewMapper
import com.lucaspuorto.marvel.presentation.viewmapper.ComicsListViewMapper

class HomeViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    fun getCharacter(characterName: String) = liveData<StateResponse<CharacterViewData>> {
        try {
            emit(StateLoading())
            val response = repository.fetchCharacter(characterName)
            val viewData = CharacterViewMapper.map(response.data.results)
            emit(StateSuccess(viewData))
        } catch (error: Throwable) {
            emit(StateError(error))
        }
    }

    fun getComics(characterId: String) = liveData<StateResponse<List<ComicsListViewData>>> {
        try {
            emit(StateLoading())
            val response = repository.fetchComicsList(characterId)
            val viewData = ComicsListViewMapper.map(response.data.results)
            emit(StateSuccess(viewData))
        } catch (error: Throwable) {
            emit(StateError(error))
        }
    }
}
