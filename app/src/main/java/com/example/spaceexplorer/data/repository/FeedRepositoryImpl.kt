package com.example.spaceexplorer.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.spaceexplorer.data.local.FeedRemoteMediator
import com.example.spaceexplorer.data.local.database.FeedDao
import com.example.spaceexplorer.data.mapper.toDomain
import com.example.spaceexplorer.data.remote.InMemoryFeedPagingSource
import com.example.spaceexplorer.data.remote.api.SpaceExplorerApi
import com.example.spaceexplorer.domain.model.FeedArticle
import com.example.spaceexplorer.domain.repository.FeedRepository
import com.example.spaceexplorer.domain.repository.SettingsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class FeedRepositoryImpl @Inject constructor(
    private val api: SpaceExplorerApi,
    private val dao: FeedDao,
    private val settingsRepository: SettingsRepository,
) : FeedRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val feedArticlesFlow: Flow<PagingData<FeedArticle>> =
        settingsRepository.offlineCachingFlow
            .distinctUntilChanged()
            .flatMapLatest { isCachingEnabled ->
                if (isCachingEnabled) {
                    cachedArticlesPager()
                } else {
                    inMemoryArticlesPager()
                }
            }

    @OptIn(ExperimentalPagingApi::class)
    fun cachedArticlesPager(): Flow<PagingData<FeedArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = FeedRemoteMediator(api = api, dao = dao),
            pagingSourceFactory = { dao.getPagedArticles() }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }

    fun inMemoryArticlesPager(): Flow<PagingData<FeedArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                initialLoadSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { InMemoryFeedPagingSource(api) }
        ).flow
            .map { pagingData ->
                pagingData.map { it.toDomain() }
            }
    }
}