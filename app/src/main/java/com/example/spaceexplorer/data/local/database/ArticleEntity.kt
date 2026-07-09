package com.example.spaceexplorer.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO add authors list property
@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String,
)
