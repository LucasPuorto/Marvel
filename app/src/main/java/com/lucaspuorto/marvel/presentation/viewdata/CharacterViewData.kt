package com.lucaspuorto.marvel.presentation.viewdata

data class CharacterViewData(
    val characterName: String,
    val characterImageUrl: String,
    val characterDescription: String,
    val characterComics: List<ComicsViewData>,
)

data class ComicsViewData(
    val comicTitle: String,
    val comicImageUrl: String
)