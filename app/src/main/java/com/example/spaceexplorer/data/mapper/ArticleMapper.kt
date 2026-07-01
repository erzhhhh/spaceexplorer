package com.example.spaceexplorer.data.mapper

import com.example.spaceexplorer.data.remote.dto.ArticleDto
import com.example.spaceexplorer.data.remote.dto.AuthorDto
import com.example.spaceexplorer.domain.model.Article
import com.example.spaceexplorer.domain.model.Author

fun ArticleDto.toDomain(): Article = Article(
    id = id,
    title = title,
    authors = authors.map { it.toDomain() },
    url = url,
    imageUrl = imageUrl,
    newsSite = newsSite,
    summary = summary,
    publishedAt = publishedAt
)

fun AuthorDto.toDomain(): Author = Author(
    name = name
)