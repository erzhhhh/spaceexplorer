package com.example.spaceexplorer.data.mapper

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

fun AuthorDto.toDomain(): Author = Author(
    name = name.orEmpty()
)