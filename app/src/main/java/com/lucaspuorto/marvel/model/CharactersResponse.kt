package com.lucaspuorto.marvel.model

import com.google.gson.annotations.SerializedName

data class CharactersResponse(
    @SerializedName("data") val data: CharacterDataContainerResponse?
)

data class CharacterDataContainerResponse(
    @SerializedName("results") val results: List<CharacterResponse>?
)

data class CharacterResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailResponse?
)

data class ThumbnailResponse(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)
