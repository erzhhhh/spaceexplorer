package com.example.spaceexplorer.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class LaunchDto(
    val results: List<LaunchArticleDto>?
)

@Serializable
data class LaunchArticleDto(
    val id: Int?,
    val title: String?,
    val authors: List<LaunchAuthorDto>?,
    val url: String?,
    @SerialName("image_url")
    val imageUrl: String?,
    @SerialName("news_site")
    val newsSite: String?,
    val summary: String?,
    @SerialName("published_at")
    val publishedAt: Instant?,
    @SerialName("updated_at")
    val updatedAt: String?,
)

@Serializable
data class LaunchAuthorDto(
    val name: String?
)
