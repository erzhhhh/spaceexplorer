package com.example.spaceexplorer.domain.model

data class FeedArticle(
    val id: Int,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
)

data class Author(
    val name: String
)