package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FeedRepository {

    val articlesFlow: Flow<List<Article>>

    suspend fun refreshArticles()
}