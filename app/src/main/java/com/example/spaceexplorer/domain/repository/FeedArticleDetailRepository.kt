package com.example.spaceexplorer.domain.repository

import com.example.spaceexplorer.domain.model.FeedArticle
import kotlinx.coroutines.flow.Flow

interface FeedArticleDetailRepository {

    fun getFeedArticle(articleId: Int): Flow<FeedArticle>

    suspend fun saveToFavorites(articleId: Int)

    suspend fun removeFromFavorites(articleId: Int)

    suspend fun toggleFavorite(articleId: Int): Boolean
}