package com.lucaspuorto.marvel.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class CharactersResponse(
    @SerializedName("code") val code: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("copyright") val copyright: String?,
    @SerializedName("attributionText") val attributionText: String?,
    @SerializedName("attributionHTML") val attributionHTML: String?,
    @SerializedName("data") val data: CharacterDataContainerResponse?,
    @SerializedName("etag") val etag: String?
)

data class CharacterDataContainerResponse(
    @SerializedName("offset") val offset: Int?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("total") val total: Int?,
    @SerializedName("count") val count: Int?,
    @SerializedName("results") val results: List<CharacterResponse>?
)

data class CharacterResponse(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("modified") val modified: Date?,
    @SerializedName("thumbnail") val thumbnail: ThumbnailResponse?
)

data class ThumbnailResponse(
    @SerializedName("path") val path: String?,
    @SerializedName("extension") val extension: String?
)
