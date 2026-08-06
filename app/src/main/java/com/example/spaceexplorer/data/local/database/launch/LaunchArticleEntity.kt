package com.example.spaceexplorer.data.local.database.launch

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.time.Instant

// TODO add authors list property
@Entity(tableName = "launch_articles")
data class LaunchArticleEntity(
    @PrimaryKey val id: Int = 0,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: Instant?,
)