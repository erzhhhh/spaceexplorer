package com.example.spaceexplorer.data.mapper

import com.example.spaceexplorer.data.local.database.favorites.FavoriteArticleEntity
import com.example.spaceexplorer.domain.model.FeedArticle

fun FeedArticle.toFavoriteEntity(): FavoriteArticleEntity = FavoriteArticleEntity(
    id = id,
    title = title,
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun FavoriteArticleEntity.toDomain() = FeedArticle(
    id = id,
    title = title,
    authors = emptyList(),
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)