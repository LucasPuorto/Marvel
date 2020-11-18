package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Price(
    @SerializedName("price") val price: String = "",
    @SerializedName("type") val type: String = ""
) : Serializable