package com.lucaspuorto.marvel.viewmodel.uistate

import com.lucaspuorto.marvel.db.model.CharacterModel

sealed class CharactersUiState {
    class Success(val charactersList: List<CharacterModel>) : CharactersUiState()
    object Error : CharactersUiState()
}

sealed class LoadingUiState {
    object Show : LoadingUiState()
    object Hide : LoadingUiState()
}

sealed class SearchCharacterUiState {
    class HasMatch(val charactersList: List<CharacterModel>) : SearchCharacterUiState()
    object HasNoMatch : SearchCharacterUiState()
    class MinCharsUnreached(val charactersList: List<CharacterModel>) : SearchCharacterUiState()
}

sealed class FavoriteButtonUiState {
    object IsFavorite : FavoriteButtonUiState()
    object NotFavorite : FavoriteButtonUiState()
}

sealed class FavoritesUiState {
    class HasFavorites(val favorites: List<CharacterModel>) : FavoritesUiState()
    object HasNoFavorites : FavoritesUiState()
}
