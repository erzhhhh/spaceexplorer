package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.FavoritesDao
import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toFavoriteEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FeedArticleDetailRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val feedDao: FeedDao,
    private val favoritesDao: FavoritesDao,
    private val settingsRepository: SettingsRepository
) : FeedArticleDetailRepository {

    override fun getFeedArticle(articleId: String): Flow<FeedArticle> {
        return feedDao.getArticle(articleId)
            .map { it.toDomain() }
    }

    override suspend fun saveToFavorites(article: FeedArticle) {
        favoritesDao.insertArticle(article.toFavoriteEntity())
    }
}