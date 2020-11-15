package com.lucaspuorto.marvel.presentation.viewmapper

import com.lucaspuorto.marvel.data.response.Results
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData

object CharacterConsultViewMapper {

    fun map(results: List<Results>): CharacterViewData =
        CharacterViewData(
            characterName = results[0].name,
            characterImageUrl = results[0].thumbnail.path + results[0].thumbnail.extension,
            characterDescription = results[0].description,
            characterId = results[0].id
        )
}