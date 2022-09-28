package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.repository.MarvelRepository
import com.lucaspuorto.marvel.viewmodel.uistate.FavoritesUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteCharactersViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val allFavoritesMutableLiveData = MutableLiveData<FavoritesUiState>()
    val allFavoritesLiveData: LiveData<FavoritesUiState> get() = allFavoritesMutableLiveData

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            val favorites = repository.getAllFavorites()
            if (favorites.isNotEmpty()) {
                allFavoritesMutableLiveData.postValue(FavoritesUiState.HasFavorites(favorites))
            } else {
                allFavoritesMutableLiveData.postValue(FavoritesUiState.HasNoFavorites)
            }
        }
    }
}