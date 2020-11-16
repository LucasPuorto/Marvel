package com.lucaspuorto.marvel.presentation.viewmapper

import com.lucaspuorto.marvel.data.response.Results
import com.lucaspuorto.marvel.presentation.viewdata.ComicsListViewData

object ComicsListViewMapper {

    fun map(results: List<Results>): List<ComicsListViewData> =
        results.map { result ->
            ComicsListViewData(
                comicTitle = result.title,
                comicImage = "${result.thumbnail.path}.${result.thumbnail.extension}"
            )
        }
}
