package com.example.spaceexplorer.data.repository

import com.example.spaceexplorer.data.local.database.FavoritesDao
import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.mapper.toEntity
import com.example.spaceexplorer.data.mapper.toFavoriteEntity
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedArticleDetailRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FeedDetailsRepositoryImpl(
    private val api: SpaceExplorerApi,
    private val feedDao: FeedDao,
    private val favoritesDao: FavoritesDao,
    private val settingsRepository: SettingsRepository
) : FeedArticleDetailRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFeedArticle(articleId: String): Flow<FeedArticle> {
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

    override suspend fun saveToFavorites(article: FeedArticle) {
        favoritesDao.insertArticle(article.toFavoriteEntity())
    }

    private fun observeArticleWithRefresh(articleId: String): Flow<FeedArticle> = flow {
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