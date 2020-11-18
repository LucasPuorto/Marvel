package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MarvelCharacterResponse(
    @SerializedName("attributionHTML") val attributionHTML: String = "",
    @SerializedName("attributionText") val attributionText: String = "",
    @SerializedName("code") val code: String = "",
    @SerializedName("copyright") val copyright: String = "",
    @SerializedName("data") val data: Data = Data(),
    @SerializedName("etag") val eTag: String = "",
    @SerializedName("status") val status: String = ""
) : Serializable