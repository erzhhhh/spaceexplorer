package com.example.spaceexplorer.domain.model

data class Launch(
    val id: String,
    val title: String,
    val authors: List<Author>,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
)
