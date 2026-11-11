package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.favorites.FavoritesDao
import com.example.spaceexplorer.data.local.database.feed.FeedDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.mapper.toFavoriteEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FeedDetailsRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val feedDao: FeedDao,
    private val favoritesDao: FavoritesDao,
    private val settingsRepository: SettingsRepository
) : FeedArticleDetailRepository {

    override fun getFeedArticle(articleId: Int): Flow<FeedArticle> {
        return combine(
            flow = loadArticle(articleId),
            flow2 = favoritesDao.isFavorite(articleId)
        ) { article, isFavorite ->
            article.copy(isFavorite = isFavorite)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadArticle(articleId: Int): Flow<FeedArticle> {
        return settingsRepository.offlineCachingFlow
            .distinctUntilChanged()
            .flatMapLatest { isCachingEnabled ->
                if (isCachingEnabled) {
                    observeArticleWithRefresh(articleId)
                } else {
                    flow { emit(api.getFeedArticle(articleId).toDomain()) }
                }
            }
    }

    override suspend fun saveToFavorites(articleId: Int) {
        val article: FeedArticle = loadArticle(articleId).first()
        favoritesDao.insertArticle(article.toFavoriteEntity())
    }

    override suspend fun removeFromFavorites(articleId: Int) {
        favoritesDao.deleteArticle(articleId)
    }

    override suspend fun toggleFavorite(articleId: Int): Boolean {
        val isFavorite = favoritesDao.isFavorite(articleId).first()
        if (isFavorite) removeFromFavorites(articleId) else saveToFavorites(articleId)
        return !isFavorite
    }

    private fun observeArticleWithRefresh(articleId: Int): Flow<FeedArticle> = flow {
        // FIXME: return immediate article from db. Refresh if in parallel
        try {
            val dto = api.getFeedArticle(articleId)
            feedDao.insertArticle(dto.toEntity())
        } catch (e: Exception) {
            // no network - working with old cache
        }
        emitAll(feedDao.getArticle(articleId).map { it.toDomain() })
    }
}