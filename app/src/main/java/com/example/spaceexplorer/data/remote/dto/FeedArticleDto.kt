package com.example.spaceexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeedDto(
    val results: List<FeedArticleDto>?
)

@Serializable
data class FeedArticleDto(
    val id: Int?,
    val title: String?,
    val authors: List<AuthorDto>?,
    val url: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("news_site")
    val newsSite: String?,
    val summary: String?,
    @SerialName("published_at")
    val publishedAt: String?,
    @SerialName("updated_at")
    val updatedAt: String?,
)

@Serializable
data class AuthorDto(
    val name: String?
)
