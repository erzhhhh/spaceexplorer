package com.example.spaceexplorer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LaunchDto(
    val results: List<LaunchArticleDto>?
)

data class LaunchArticleDto(
    val id: Int?,
    val title: String?,
    val authors: List<LaunchAuthorDto>?,
    val url: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("news_site")
    val newsSite: String?,
    val summary: String?,
    @SerializedName("published_at")
    val publishedAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
)

data class LaunchAuthorDto(
    val name: String?
)
