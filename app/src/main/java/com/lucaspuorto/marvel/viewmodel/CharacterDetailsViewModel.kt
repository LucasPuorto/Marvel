package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.viewmodel.uistate.FavoriteButtonUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val favoriteMutableLiveData = MutableLiveData<FavoriteButtonUiState>()
    val favoriteLiveData: LiveData<FavoriteButtonUiState> get() = favoriteMutableLiveData

    fun setFavoriteButtonState(isFavorite: Boolean) {
        if (isFavorite)
            favoriteMutableLiveData.postValue(FavoriteButtonUiState.IsFavorite)
        else
            favoriteMutableLiveData.postValue(FavoriteButtonUiState.NotFavorite)
    }

    fun favoriteStateChange(character: CharacterModel) {
        if (character.isFavorite) removingFromFavorite(character)
        else addingAsFavorite(character)
    }

    private fun addingAsFavorite(character: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            character.isFavorite = true
            repository.addingAsFavorite(character)
            favoriteMutableLiveData.postValue(FavoriteButtonUiState.IsFavorite)
        }
    }

    private fun removingFromFavorite(character: CharacterModel) {
        viewModelScope.launch(Dispatchers.IO) {
            character.isFavorite = false
            repository.removingFromFavorite(character)
            favoriteMutableLiveData.postValue(FavoriteButtonUiState.NotFavorite)
        }
    }
}