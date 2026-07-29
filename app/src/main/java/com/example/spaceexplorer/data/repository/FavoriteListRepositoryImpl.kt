package com.example.spaceexplorer.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spaceexplorer.data.local.database.FavoritesDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FavoritesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesListRepositoryImpl @Inject constructor(
    private val dao: FavoritesDao
) : FavoritesListRepository {

    override val favoriteArticlesFlow: Flow<PagingData<FeedArticle>> =
        cachedArticlesPager()

    private fun cachedArticlesPager(): Flow<PagingData<FeedArticle>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { dao.getFavoriteArticles() },
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }
}