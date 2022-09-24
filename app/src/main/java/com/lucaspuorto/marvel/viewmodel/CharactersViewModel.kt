package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.viewmodel.mapper.CharacterResponseMapper
import com.lucaspuorto.marvel.viewmodel.uistate.CharactersUiState
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val charactersMutableLiveData = MutableLiveData<CharactersUiState>()
    val charactersLiveData: LiveData<CharactersUiState> get() = charactersMutableLiveData

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            val response = repository.getCharacters()
            if (response.isSuccessful) {
                val mappedResponse = CharacterResponseMapper().transform(response.body())
                charactersMutableLiveData.postValue(CharactersUiState.Success(mappedResponse.data.results))
            } else {
                charactersMutableLiveData.postValue(CharactersUiState.Error)
            }
        }
    }
}
