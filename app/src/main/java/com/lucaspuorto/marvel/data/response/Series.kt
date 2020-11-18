package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Series(
    @SerializedName("available") val available: Int = 0,
    @SerializedName("collectionURI") val collectionURI: String = "",
    @SerializedName("items") val items: List<Item> = emptyList(),
    @SerializedName("returned") val returned: Int = 0,

    @SerializedName("name") val name: String = "",
    @SerializedName("resourceURI") val resourceURI: String = ""
) : Serializable