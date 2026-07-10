package com.example.spaceexplorer.data.mapper

import com.example.spaceexplorer.data.local.database.ArticleEntity
import com.example.spaceexplorer.data.remote.dto.ArticleDto
import com.example.spaceexplorer.data.remote.dto.AuthorDto
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.model.Author

fun ArticleDto.toDomain(): Article = Article(
    id = id ?: 0,
    title = title.orEmpty(),
    authors = authors?.map { it.toDomain() }.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)

fun ArticleDto.toEntity(): ArticleEntity = ArticleEntity(
    id = id ?: 0,
    title = title.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)

fun Article.toEntity(): ArticleEntity = ArticleEntity(
    id = id,
    title = title,
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun ArticleEntity.toDomain(): Article = Article(
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