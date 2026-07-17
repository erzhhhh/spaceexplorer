package com.example.spaceexplorer.data.mapper

import com.example.spaceexplorer.data.local.database.LaunchArticleEntity
import com.example.spaceexplorer.data.remote.dto.LaunchArticleDto
import com.example.spaceexplorer.data.remote.dto.LaunchAuthorDto
import com.example.spaceexplorer.domain.model.Author
import com.example.spaceexplorer.domain.model.LaunchArticle

fun LaunchArticleDto.toDomain(): LaunchArticle = LaunchArticle(
    id = id ?: 0,
    title = title.orEmpty(),
    authors = authors?.map { it.toDomain() }.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)

fun LaunchAuthorDto.toDomain(): Author = Author(
    name = name.orEmpty()
)

fun LaunchArticleEntity.toDomain() = LaunchArticle(
    id = id,
    title = title,
    authors = emptyList(),
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun LaunchArticleDto.toEntity(): LaunchArticleEntity = LaunchArticleEntity(
    id = id ?: 0,
    title = title.orEmpty(),
    url = url.orEmpty(),
    imageUrl = imageUrl.orEmpty(),
    newsSite = newsSite.orEmpty(),
    summary = summary.orEmpty(),
    publishedAt = publishedAt.orEmpty()
)