package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.Article

interface FeedRepository {
    suspend fun getArticles(): List<Article>
}