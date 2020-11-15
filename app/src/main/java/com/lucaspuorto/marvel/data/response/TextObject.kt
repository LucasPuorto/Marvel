package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TextObject(
    @SerializedName("language") val language: String,
    @SerializedName("text") val text: String,
    @SerializedName("type") val type: String
) : Serializable