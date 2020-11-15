package com.lucaspuorto.marvel.presentation.viewmapper

import com.lucaspuorto.marvel.data.response.Results
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.presentation.viewdata.ComicsViewData

object CharacterConsultViewMapper {

    fun map(results: List<Results>): CharacterViewData =
        CharacterViewData(
            characterName = results[0].name,
            characterImageUrl = results[0].thumbnail.path + results[0].thumbnail.extension,
            characterDescription = results[0].description,
            characterComics = listOf(
                ComicsViewData(
                    comicTitle = results[0].comics.items[0].name,
                    comicImageUrl = results[0].comics.items[0].resourceURI
                )
            )
        )
}