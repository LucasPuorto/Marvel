package com.lucaspuorto.marvel.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.data.repository.MarvelRepository
import com.lucaspuorto.marvel.presentation.StateError
import com.lucaspuorto.marvel.presentation.StateLoading
import com.lucaspuorto.marvel.presentation.StateResponse
import com.lucaspuorto.marvel.presentation.StateSuccess
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewmapper.CharacterViewMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private val repository: MarvelRepository = MarvelRepository()

    private val _characterLiveData: MutableLiveData<StateResponse<CharacterViewData>> = MutableLiveData()
    val characterLiveData: LiveData<StateResponse<CharacterViewData>> get() = _characterLiveData

    fun fetchCharacter(characterName: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            _characterLiveData.postValue(StateLoading())
            try {
                val response = repository.fetchCharacter(characterName)
                val viewData = CharacterViewMapper.map(response.data.results)
                _characterLiveData.postValue(StateSuccess(viewData))
            } catch (error: Exception) {
                _characterLiveData.postValue(StateError(error))
            }
        }
    }
}
