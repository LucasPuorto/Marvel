package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.viewmodel.mapper.CharacterResponseMapper
import com.lucaspuorto.marvel.viewmodel.uistate.CharactersUiState
import com.lucaspuorto.marvel.viewmodel.uistate.LoadingUiState
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val charactersMutableLiveData = MutableLiveData<CharactersUiState>()
    val charactersLiveData: LiveData<CharactersUiState> get() = charactersMutableLiveData

    private val loadingMutableLiveData = MutableLiveData<LoadingUiState>()
    val loadingLiveData: LiveData<LoadingUiState> get() = loadingMutableLiveData

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(LoadingUiState.Show)
            val response = repository.getCharacters()
            response.body()?.data?.let { responseData ->
                if (response.isSuccessful) {
                    charactersMutableLiveData.postValue(
                        CharactersUiState.Success(
                            CharacterResponseMapper().transform(responseData.results)
                        )
                    )
                    loadingMutableLiveData.postValue(LoadingUiState.Hide)
                } else {
                    setCharactersErrorState()
                }
            } ?: setCharactersErrorState()
        }
    }

    private fun setCharactersErrorState() {
        charactersMutableLiveData.postValue(CharactersUiState.Error)
        loadingMutableLiveData.postValue(LoadingUiState.Hide)
    }
}
