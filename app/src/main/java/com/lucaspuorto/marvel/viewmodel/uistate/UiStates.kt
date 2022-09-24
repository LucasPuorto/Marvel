package com.lucaspuorto.marvel.viewmodel.uistate

import com.lucaspuorto.marvel.model.CharacterModel

sealed class CharactersUiState {
    class Success(val charactersList: List<CharacterModel>) : CharactersUiState()
    object Error : CharactersUiState()
}

sealed class LoadingUiState {
    object Show : LoadingUiState()
    object Hide : LoadingUiState()
}