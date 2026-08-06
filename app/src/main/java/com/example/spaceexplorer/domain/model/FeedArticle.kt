package com.example.spaceexplorer.domain.model

import kotlin.time.Instant

data class FeedArticle(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: Instant?,
    val isFavorite: Boolean = false
)

data class Author(
    val name: String
)