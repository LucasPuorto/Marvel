package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Characters(
    @SerializedName("available") val available: String = "",
    @SerializedName("collectionURI") val collectionURI: String = "",
    @SerializedName("items") val items: List<Item> = emptyList(),
    @SerializedName("returned") val returned: String = ""
) : Serializable