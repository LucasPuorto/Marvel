package com.lucaspuorto.marvel.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Data(
    @SerializedName("count") val count: String,
    @SerializedName("limit") val limit: String,
    @SerializedName("offset") val offset: String,
    @SerializedName("results") val results: List<Results>,
    @SerializedName("total") val total: String
) : Serializable