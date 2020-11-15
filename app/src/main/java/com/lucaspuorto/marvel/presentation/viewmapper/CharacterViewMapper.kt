package com.lucaspuorto.marvel.presentation.viewmapper

import com.lucaspuorto.marvel.data.response.Results
import com.lucaspuorto.marvel.presentation.viewdata.CharacterViewData
import com.lucaspuorto.marvel.utils.CHARACTER_LIST_FIRST_POSITION

object CharacterViewMapper {

    fun map(results: List<Results>): CharacterViewData =
        CharacterViewData(
            characterName = results[CHARACTER_LIST_FIRST_POSITION].name,
            characterImageUrl = "${results[CHARACTER_LIST_FIRST_POSITION].thumbnail.path}.${results[CHARACTER_LIST_FIRST_POSITION].thumbnail.extension}",
            characterDescription = results[CHARACTER_LIST_FIRST_POSITION].description,
            characterId = results[CHARACTER_LIST_FIRST_POSITION].id
        )
}