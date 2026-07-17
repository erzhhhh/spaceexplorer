package com.example.spaceexplorer.data.mapper

import com.example.spaceexplorer.data.local.database.FeedArticleEntity
import com.example.spaceexplorer.data.remote.dto.FeedArticleDto
import com.example.spaceexplorer.data.remote.dto.AuthorDto
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.model.Author

fun FeedArticleDto.toDomain(): FeedArticle = FeedArticle(
    id = id ?: 0,
    title = title.orEmpty(),
    authors = authors?.map { it.toDomain() }.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)

fun FeedArticleDto.toEntity(): FeedArticleEntity = FeedArticleEntity(
    id = id ?: 0,
    title = title.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)

fun FeedArticle.toEntity(): FeedArticleEntity = FeedArticleEntity(
    id = id,
    title = title,
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun FeedArticleEntity.toDomain(): FeedArticle = FeedArticle(
    id = id,
    title = title,
    authors = emptyList(),
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun AuthorDto.toDomain(): Author = Author(
    name = name.orEmpty()
)