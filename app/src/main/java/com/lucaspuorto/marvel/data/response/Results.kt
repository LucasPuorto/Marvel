package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Results(
    @SerializedName("comics") val comics: Comics,
    @SerializedName("description") val description: String,
    @SerializedName("events") val events: Events,
    @SerializedName("id") val id: String,
    @SerializedName("modified") val modified: String,
    @SerializedName("name") val name: String,
    @SerializedName("resourceURI") val resourceURI: String,
    @SerializedName("series") val series: Series,
    @SerializedName("stories") val stories: Stories,
    @SerializedName("thumbnail") val thumbnail: Thumbnail,
    @SerializedName("urls") val urls: List<Url>,

    @SerializedName("characters") val characters: Characters,
    @SerializedName("collectedIssues") val collectedIssues: List<Item>,
    @SerializedName("collections") val collections: List<Item>,
    @SerializedName("creators") val creators: Characters,
    @SerializedName("dates") val dates: List<Date>,
    @SerializedName("diamondCode") val diamondCode: String,
    @SerializedName("digitalId") val digitalId: String,
    @SerializedName("ean") val ean: String,
    @SerializedName("format") val format: String,
    @SerializedName("images") val images: List<Thumbnail>,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("issn") val issn: String,
    @SerializedName("issueNumber") val issueNumber: Double,
    @SerializedName("pageCount") val pageCount: String,
    @SerializedName("prices") val prices: List<Price>,
    @SerializedName("textObjects") val textObjects: List<TextObject>,
    @SerializedName("title") val title: String,
    @SerializedName("upc") val upc: String,
    @SerializedName("variantDescription") val variantDescription: String,
    @SerializedName("variants") val variants: List<Item>
) : Serializable