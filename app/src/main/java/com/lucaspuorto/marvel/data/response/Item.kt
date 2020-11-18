package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Item(
    @SerializedName("name") val name: String = "",
    @SerializedName("resourceURI") val resourceURI: String = "",
    @SerializedName("type") val type: String = ""
) : Serializable