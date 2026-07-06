package com.example.spaceexplorer.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FeedDto(
    val results: List<ArticleDto>?
)

data class ArticleDto(
    val id: Int?,
    val title: String?,
    val authors: List<AuthorDto>?,
    val url: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val newsSite: String?,
    val summary: String?,
    val publishedAt: String?,
    val updatedAt: String?,
)

data class AuthorDto(
    val name: String?
)
