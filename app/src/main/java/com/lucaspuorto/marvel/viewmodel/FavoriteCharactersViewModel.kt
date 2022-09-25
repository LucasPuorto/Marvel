package com.lucaspuorto.marvel.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lucaspuorto.marvel.db.model.CharacterModel
import com.lucaspuorto.marvel.repository.MarvelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteCharactersViewModel(
    private val repository: MarvelRepository
) : ViewModel() {

    private val allFavoritesMutableLiveData = MutableLiveData<List<CharacterModel>>()
    val allFavoritesLiveData: LiveData<List<CharacterModel>> get() = allFavoritesMutableLiveData

    init {
        getAllFavorites()
    }

    private fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            allFavoritesMutableLiveData.postValue(repository.getAllFavorites())
        }
    }
}