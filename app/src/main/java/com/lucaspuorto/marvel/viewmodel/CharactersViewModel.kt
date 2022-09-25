package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.viewmodel.mapper.CharacterResponseMapper
import com.lucaspuorto.marvel.viewmodel.uistate.CharactersUiState
import com.lucaspuorto.marvel.viewmodel.uistate.LoadingUiState
import com.lucaspuorto.marvel.viewmodel.uistate.SearchCharacterUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    companion object {
        private const val MIN_CHARS_TO_SEARCH = 3
    }

    private val charactersMutableLiveData = MutableLiveData<CharactersUiState>()
    val charactersLiveData: LiveData<CharactersUiState> get() = charactersMutableLiveData

    private val loadingMutableLiveData = MutableLiveData<LoadingUiState>()
    val loadingLiveData: LiveData<LoadingUiState> get() = loadingMutableLiveData

    private val searchMutableLiveData = MutableLiveData<SearchCharacterUiState>()
    val searchLiveData: LiveData<SearchCharacterUiState> get() = searchMutableLiveData

    private val allCharacters = mutableListOf<CharacterModel>()

    init {
        getCharacters()
    }

    private fun getCharacters() {
        viewModelScope.launch {
            loadingMutableLiveData.postValue(LoadingUiState.Show)
            val response = repository.getCharacters()
            response.body()?.data?.let { responseData ->
                val mappedData = CharacterResponseMapper().transform(responseData.results)

                if (response.isSuccessful) {
                    allCharacters.addAll(mappedData)
                    charactersMutableLiveData.postValue(CharactersUiState.Success(mappedData))
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

    fun searchFilter(searchText: String?) {
        viewModelScope.launch(Dispatchers.Default) {
            val auxCharacters = mutableListOf<CharacterModel>()
            if (searchText != null && searchText.length >= MIN_CHARS_TO_SEARCH) {
                allCharacters.map { character ->
                    if (character.name.contains(searchText.toString(), true))
                        auxCharacters.add(character)
                }
                if (auxCharacters.isEmpty())
                    searchMutableLiveData.postValue(SearchCharacterUiState.HasNoMatch)
                else
                    searchMutableLiveData.postValue(SearchCharacterUiState.HasMatch(auxCharacters))
            } else {
                auxCharacters.clear()
                searchMutableLiveData.postValue(SearchCharacterUiState.MinCharsUnreached(allCharacters))
            }
        }
    }

    fun favoriteStateChange(character: CharacterModel) {
        if (character.isFavorite) removingFromFavorite(character)
        else addingAsFavorite(character)
    }

    private fun addingAsFavorite(character: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            character.isFavorite = true
            repository.addingAsFavorite(character)
        }
    }

    private fun removingFromFavorite(character: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            character.isFavorite = false
            repository.removingFromFavorite(character)
        }
    }
}
